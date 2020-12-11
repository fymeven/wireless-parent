package cn._51even.wireless.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * 系统初始化工作类
 */
@Component
public class SystemInitRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {
        logger.debug("---进入系统初始化工作---");
        logger.debug("---系统初始化工作结束---");
    }

}
