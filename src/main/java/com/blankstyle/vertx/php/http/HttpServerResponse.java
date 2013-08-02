package com.blankstyle.vertx.php.http;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ExceptionSupport;
import com.blankstyle.vertx.php.streams.WriteStream;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpServerResponse.
 *
 * @author Jordan Halterman
 */
public class HttpServerResponse implements WriteStream<HttpServerResponse>, ExceptionSupport<HttpServerResponse> {

  private org.vertx.java.core.http.HttpServerResponse response;

  public HttpServerResponse(org.vertx.java.core.http.HttpServerResponse response) {
    this.response = response;
  }

  public HttpServerResponse(Env env, org.vertx.java.core.http.HttpServerResponse response) {
    this.response = response;
  }

  /**
   * Returns the response status code.
   */
  public Value getStatusCode(Env env) {
    return env.wrapJava(response.getStatusCode());
  }

  /**
   * Sets the response status code.
   */
  public Value setStatusCode(Env env, NumberValue statusCode) {
    response.setStatusCode(statusCode.toInt());
    return env.wrapJava(this);
  }

  /**
   * Returns the response status message.
   */
  public Value getStatusMessage(Env env) {
    return env.createString(response.getStatusMessage());
  }

  /**
   * Sets the response status message.
   */
  public HttpServerResponse setStatusMessage(Env env, StringValue message) {
    response.setStatusMessage(message.toString());
    return this;
  }

  /**
   * Returns response headers.
   */
  public Value headers(Env env) {
    return env.wrapJava(response.headers());
  }

  /**
   * Returns response trailers.
   */
  public Value trailers(Env env) {
    return env.wrapJava(response.trailers());
  }

  /**
   * Puts an HTTP header.
   */
  public HttpServerResponse putHeader(Env env, StringValue name, Value value) {
    response.putHeader(name.toString(), value.toString());
    return this;
  }

  /**
   * Sends a file to the client.
   */
  public HttpServerResponse sendFile(Env env, StringValue filename) {
    response.sendFile(filename.toString());
    return this;
  }

  @Override
  public HttpServerResponse write(Env env, Value data, @Optional StringValue enc) {
    if (enc != null && !enc.isDefault()) {
      response.write(data.toString(), enc.toString());
    }
    else {
      response.write(data.toString());
    }
    return this;
  }

  public void end() {
    response.end();
  }

  public void end(Env env) {
    end();
  }

  public void end(Env env, Value data) {
    response.end(data.toString());
  }

  @Override
  public HttpServerResponse drainHandler(Env env, Callback handler) {
    response.drainHandler(new Handler<Void>(env, handler));
    return this;
  }

  public HttpServerResponse closeHandler(Env env, Callback handler) {
    response.closeHandler(new Handler<Void>(env, handler));
    return this;
  }

  public HttpServerResponse setChunked(Env env, BooleanValue chunked) {
    response.setChunked(chunked.toBoolean());
    return this;
  }

  public BooleanValue isChunked(Env env) {
    return BooleanValue.create(response.isChunked());
  }

  @Override
  public HttpServerResponse setWriteQueueMaxSize(Env env, NumberValue size) {
    response.setWriteQueueMaxSize(size.toInt());
    return this;
  }

  @Override
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(response.writeQueueFull());
  }

  @Override
  public HttpServerResponse exceptionHandler(Env env, Callback handler) {
    response.exceptionHandler(new Handler<Throwable>(env, handler));
    return this;
  }

}