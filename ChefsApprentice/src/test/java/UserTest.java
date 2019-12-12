import static org.junit.Assert.*;
import org.junit.*;

// project
import sample.User;


public class UserTest {

    @Test
    public void isGuestTest() {
        User user = new User("Ola", "guest");
        assertSame("guest", user.getRole());
    }

    @Test
    public void isRegularUserTest() {
        User user = new User("Ola", "regularUser");
        assertSame("regularUser", user.getRole());
    }

    @Test
    public void isChefTest() {
        User user = new User("Ola", "chef");
        assertSame("chef", user.getRole());
    }

    @Test
    public void isAdminTest() {
        User user = new User("Ola", "admin");
        assertSame("admin", user.getRole());
    }

    @Test
    public void printUserTest(){
        User user = new User("Test", "admin");
        try{
        user.printUser();
        }catch(Exception e) {
            fail("Threw Exception!");
        }
    }

    @Test
    public void getterTest(){
        User user = new User("test", "admin");
        assertEquals("test", user.getUsername());
        assertEquals("admin", user.getRole());
    }

}
