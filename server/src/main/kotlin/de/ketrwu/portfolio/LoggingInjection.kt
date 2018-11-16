package de.ketrwu.portfolio

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.declaredMemberProperties

/**
 * Annotation to mark properties where a slf4j logger should be injected
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Slf4j

/**
 * BeanPostProcessor that injects a slf4j logger in annotated properties
 * @author Kenneth Wußmann
 */
@Component
class LoggingInjector : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String) =
        bean.also {
            try {
                val loggerName = it::class.java.canonicalName
                processObject(it, loggerName)
                it::class.companionObjectInstance?.let { companion ->
                    processObject(companion, loggerName)
                }
            } catch (ignored: Throwable) {
                // ignore exceptions, keep the object as it is. not every required class may be found on the classpath
                // as SpringBoot tries to load not existing stuff as well
            }
        }

    private fun processObject(target: Any, loggerName: String) {
        target::class.declaredMemberProperties.forEach { property ->
            if (property is KMutableProperty<*>) {
                property.annotations
                    .filterIsInstance<Slf4j>()
                    .forEach {
                        property.setter.call(target, LoggerFactory.getLogger(loggerName))
                    }
            }
        }
    }
}