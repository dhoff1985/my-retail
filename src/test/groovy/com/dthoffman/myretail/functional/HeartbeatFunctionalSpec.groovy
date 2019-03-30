package com.dthoffman.myretail.functional

import com.dthoffman.myretail.Application
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(application = Application)
class HeartbeatFunctionalSpec extends Specification {

  @Inject
  @Client('/')
  HttpClient client

  def "returns heartbeat" () {
    when:
    Map response = client.toBlocking().retrieve('heartbeat', Map)

    then:
    response.application == "myretail"
    response.version != null
  }
}
