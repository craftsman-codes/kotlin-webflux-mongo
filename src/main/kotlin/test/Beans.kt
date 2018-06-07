package test

import com.samskivert.mustache.Mustache
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions
import test.web.Routes
import test.web.UserHandler
import test.web.view.MustacheResourceTemplateLoader
import test.web.view.MustacheViewResolver

fun beans() = beans {
  bean<UserHandler>()
  bean<Routes>()
  bean("webHandler") {
    RouterFunctions.toWebHandler(ref<Routes>().router(), HandlerStrategies.builder().viewResolver(ref()).build())
  }
  bean("messageSource") {
    ReloadableResourceBundleMessageSource().apply {
      setBasename("messages")
      setDefaultEncoding("UTF-8")
    }
  }
  bean {
    val prefix = "classpath:/templates/"
    val suffix = ".mustache"
    val loader = MustacheResourceTemplateLoader(prefix, suffix)
    MustacheViewResolver(Mustache.compiler().withLoader(loader)).apply {
      setPrefix(prefix)
      setSuffix(suffix)
    }
  }
}
