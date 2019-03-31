package com.dthoffman.myretail.functional

class HeartbeatFunctionalSpec extends BaseFunctionalSpec {

  def "returns heartbeat"() {
    when:
    Map response = client.toBlocking().retrieve('heartbeat', Map)

    then:
    response.application == "myretail"
    response.version != null
  }
}
