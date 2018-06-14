package test

import org.springframework.context.support.GenericApplicationContext
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.ipc.netty.http.server.HttpServer
import reactor.ipc.netty.tcp.BlockingNettyContext

class Application {

  private val httpHandler: HttpHandler

  private val server: HttpServer

  private var nettyContext: BlockingNettyContext? = null

  constructor(port: Int = 8080) {
    val context = GenericApplicationContext().apply {
      beans().initialize(this)
      refresh()
    }

    server = HttpServer.create(port)
    httpHandler = WebHttpHandlerBuilder
        .applicationContext(context)
        .build()
  }

  fun startAndAwait() {
    server.startAndAwait(ReactorHttpHandlerAdapter(httpHandler), { nettyContext = it })
  }
}

fun main(args: Array<String>) {
  Application().startAndAwait()
}