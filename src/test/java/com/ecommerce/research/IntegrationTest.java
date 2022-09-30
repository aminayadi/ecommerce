package com.ecommerce.research;

import com.ecommerce.research.ResearchdbApp;
import com.ecommerce.research.config.AsyncSyncConfiguration;
import com.ecommerce.research.config.EmbeddedMongo;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ResearchdbApp.class, AsyncSyncConfiguration.class })
@EmbeddedMongo
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
