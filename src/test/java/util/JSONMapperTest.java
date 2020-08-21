package util;

import com.jbgroup.tuogubao.model.User;
import com.jbgroup.tuogubao.model.UserBuilder;
import com.jbgroup.tuogubao.util.JSONMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class JSONMapperTest {

    public static class TestClass {
        private static String name;
        private static String item;

        public TestClass() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }
    }

    public static class TestClassWithBuilder {
        private static String name;
        private static String item;

        public TestClassWithBuilder() {}

        public TestClassWithBuilder(String name, String item) {
            this.name = name;
            this.item = item;
        }

        public static class Builder {
            private static String name;
            private static String item;

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setItem(String item) {
                this.item = item;
                return this;
            }

            public TestClassWithBuilder build() {
                return new TestClassWithBuilder(this.name, this.item);
            }
        }

    }

    @Ignore
    @Test
    public void MapStringWithQuotes() {
        String testStr = "{\"username\": \"caonima\", \"password\": \"nimabi\"}";
        Class<TestClass> c = TestClass.class;
        JSONMapper<TestClass, TestClass> tcMapper = new JSONMapper<>(c, testStr);
        tcMapper.setJsonStr(testStr);
        TestClass tc = tcMapper.parse();
//        tcMapper.test();

        assertThat(tc.name, is("caonima"));
        assertThat(tc.item, is("nimabi"));

        Class<TestClassWithBuilder> cb = TestClassWithBuilder.class;
        JSONMapper<TestClassWithBuilder, TestClassWithBuilder.Builder> tcbMapper = new JSONMapper<>(cb, testStr);
        tcbMapper.setJsonStr(testStr);
        TestClass tcb = tcMapper.parse();
//        tcMapper.test();

        assertThat(tcb.name, is("caonima"));
        assertThat(tcb.item, is("nimabi"));
    }

    @Test
    public void MapStringWithBuiderWithQuotes() {
        String testStr = "{\"username\": \"caonima\", \"password\": \"nimabi\"}";
        Class<User> cb = User.class;
        JSONMapper<User, UserBuilder> tcbMapper = new JSONMapper<>(cb, testStr);
        tcbMapper.setJsonStr(testStr);
        User tcb = tcbMapper.parse();
//        tcMapper.test();

        assertThat(tcb.getUsername(), is("caonima"));
        assertThat(tcb.getPassword(), is("nimabi"));
    }

}
