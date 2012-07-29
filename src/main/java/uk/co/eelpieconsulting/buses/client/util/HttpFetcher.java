package uk.co.eelpieconsulting.buses.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import uk.co.eelpieconsulting.buses.client.exceptions.HttpFetchException;

public class HttpFetcher {
	
	private static final int HTTP_TIMEOUT = 15000;

	public HttpFetcher() {
	}
	
	public String fetchContent(String url, String charEncoding) throws HttpFetchException {
		return httpFetch(url);		
	}
	
	private String httpFetch(String uri) throws HttpFetchException {
		try {
			final HttpGet get = new HttpGet(uri);
			get.addHeader(new BasicHeader("Accept-Encoding", "gzip"));

			final HttpResponse response = executeRequest(get);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				return new String(EntityUtils.toByteArray(response.getEntity()), "UTF-8");
			}
			
			EntityUtils.consume(response.getEntity());			
			throw new HttpFetchException();	// TODO be more specific. ie HTTP/1.1 416 Requested Range Not Satisfiable
			
		} catch (Exception e) {
			throw new HttpFetchException();
		}		
	}
	
	private HttpResponse executeRequest(HttpRequestBase request) throws IOException, ClientProtocolException {
		return setupHttpClient().execute(request);
	}
	
	private HttpClient setupHttpClient() {
		HttpClient client = new DefaultHttpClient();
		((AbstractHttpClient) client)
				.addRequestInterceptor(new HttpRequestInterceptor() {
					public void process(final HttpRequest request,
							final HttpContext context) throws HttpException,
							IOException {
						if (!request.containsHeader("Accept-Encoding")) {
							request.addHeader("Accept-Encoding", "gzip");
						}
					}
				});
		
		((AbstractHttpClient) client).addResponseInterceptor(new HttpResponseInterceptor() {
			public void process(final HttpResponse response, final HttpContext context) throws HttpException, IOException {
				HttpEntity entity = response.getEntity();
				Header ceheader = entity.getContentEncoding();
				if (ceheader != null) {
					HeaderElement[] codecs = ceheader.getElements();
					for (int i = 0; i < codecs.length; i++) {
						if (codecs[i].getName().equalsIgnoreCase("gzip")) {
							response.setEntity(new GzipDecompressingEntity(response.getEntity()));
							return;
						}
					}
				}
			}
		});
		
		client.getParams().setParameter("http.socket.timeout", new Integer(HTTP_TIMEOUT));
		client.getParams().setParameter("http.connection.timeout", new Integer(HTTP_TIMEOUT));
		return client;
	}	

	private static class GzipDecompressingEntity extends HttpEntityWrapper {

		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException, IllegalStateException {
			InputStream wrappedin = wrappedEntity.getContent();
			return new GZIPInputStream(wrappedin);
		}
		
		@Override
		public long getContentLength() {
			return this.wrappedEntity.getContentLength();
		}
	}

}
