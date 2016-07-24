package org.droolsfiddle.it;

import org.drools.compiler.compiler.DroolsParserException;
import org.droolsfiddle.rest.DrlParserService;
import org.droolsfiddle.rest.Message;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.File;


/**
 * Created by gurfm on 25/06/16.
 */
@RunWith(Arquillian.class)
@Category(IntegrationTest.class)
//@SpringConfiguration("applicationContext.xml")
public class DrlParserIT {

    @Deployment(testable = false)
    public static Archive deploy(){
        return TestUtils.getArchive();
    }

    @Test
    public void postDrlParserTest(@ArquillianResteasyResource DrlParserService service) throws DroolsParserException {

        // Given

        Message request = new Message();

        request.setData("import java.util.Date\n" +
                "\n" +
                "declare Address\n" +
                "    name : String\n" +
                "end\n" +
                "\n" +
                "declare Person\n" +
                "    name : String\n" +
                "    dateOfBirth : Date\n" +
                "    address : Address\n" +
                "end\n" +
                "\n" +
                "rule \"Using a declared Type\"\n" +
                "when\n" +
                "    $p : Person( name == \"Bob\" )\n" +
                "then\n" +
                "    // Insert Mark, who is Bob's mate.\n" +
                "    Person mark = new Person();\n" +
                "    mark.setName(\"Mark\");\n" +
                "    insert( mark );\n" +
                "end");

        // When

        final Message response = service.postDrlParser(request);

        // Then

        System.out.println(response.getPackages());
    }
}