package local.tux.app.dubbox;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
//@ImportResource("classpath:META-INF/spring/applicationContext.xml")*/
@ImportResource(locations = {"config/dubbo-consumer.xml"})
public class DubboConfig {

}	