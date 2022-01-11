package com.bifos.springsecurity.bpp

import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component


/**
 * CustomBeanPostProcessor
 * http://stackoverflow.com/questions/1201726/tracking-down-cause-of-springs-not-eligible-for-auto-proxying/19688634#19688634
 */
@Component
class CustomBeanPostProcessor : BeanPostProcessor {

    companion object {
        private val logger = LoggerFactory.getLogger(CustomBeanPostProcessor::class.java)
    }

    init {
        logger.trace("*******************************************************")
        logger.trace("*******************************************************")
        logger.trace("0. Spring calls constructor")
        logger.trace("*******************************************************")
        logger.trace("*******************************************************")
    }

    /**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(
        bean: Any,
        beanName: String
    ): Any {
        logger.trace("*******************************************************")
        logger.trace(bean.javaClass.toString() + "  " + beanName)
        return bean
    }

    /**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(
        bean: Any,
        beanName: String
    ): Any {
        logger.trace("*******************************************************")
        logger.trace(bean.javaClass.toString() + "  " + beanName)
        return bean
    }
}