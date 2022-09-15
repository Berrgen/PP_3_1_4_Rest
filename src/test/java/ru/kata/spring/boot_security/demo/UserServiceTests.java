package ru.kata.spring.boot_security.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserServiceTests {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("alexS@gmail.com");
        user.setPassword("222");
        user.setFirstName("Alex");
        user.setLastName("Slyckiy");
        user.setAge((byte) 25);

        User savedUser = userRepo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "sergeu1@gmail.com";
        User user = userRepo.findByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testAddRoleToNewUser() {
        Role roleUser = roleRepo.findByName("ADMIN");

        User user = new User();
        user.setEmail("admin@mail.ru");
        user.setPassword("admin");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setAge((byte) 25);
        user.addRole(roleUser);

        User savedUser = userRepo.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(1);
    }

    @Test
    public void testAddRoleToExistingUser() {
        User user = userRepo.findById(1L).get();

        Role roleUser = roleRepo.findByName("ADMIN");
        user.addRole(roleUser);

        Role roleAdmin = new Role(2L);
        user.addRole(roleAdmin);

        User savedUser = userRepo.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(1);
    }
}
