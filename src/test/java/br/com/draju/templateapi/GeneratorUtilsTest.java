package br.com.draju.templateapi;

import org.junit.jupiter.api.*;

@DisplayName("Docx GeneratorUtils test")
public class GeneratorUtilsTest {

    @BeforeEach
    void setUp() {
        //implement here
    }

    @AfterEach
    void tearDown() {
        //implement here
    }

    @BeforeAll
    void initializeTest() {
        //here
    }

    @AfterAll
    void finalizeTest() {
        //here
    }

    @Test
    @DisplayName("starts with no alert status")
    void startsWithNoAlert() {
        Assertions.assertEquals("", "");
    }
    /*
        TODO: Other JUnit 5 examples to use
    *
    @ParameterizedTest(name = "User {1}, when Alert level is {2} should have access to transporters of {0}")
    @MethodSource("transporterTestProvider")
    void returnsCorrectAccessForTransporter(boolean expected, User user, Alert alertStatus) {
        testee.setAlertStatus(alertStatus);
        assertEquals(expected, testee.canAccessTransporter(user),
                () -> generateFailureMessage("transporter", expected, user, alertStatus));
    }

    private static Stream<Arguments> transporterTestProvider() {
        return Stream.of(
                Arguments.of(true,  picard,  Alert.NONE),
                Arguments.of(true,  barclay, Alert.NONE),
                Arguments.of(false, lwaxana, Alert.NONE),
                Arguments.of(false, q,       Alert.NONE),
                Arguments.of(true,  picard,  Alert.YELLOW),
                Arguments.of(true,  barclay, Alert.YELLOW),
                Arguments.of(false, lwaxana, Alert.YELLOW),
                Arguments.of(false, q,       Alert.YELLOW),
                Arguments.of(true,  picard,  Alert.RED),
                Arguments.of(false, barclay, Alert.RED),
                Arguments.of(false, lwaxana, Alert.RED),
                Arguments.of(false, q,       Alert.RED)
        );
    }

    private String generateFailureMessage(String system, boolean expected, User user, Alert alertStatus) {
        String message = user.getName() + " should";
        if (!expected) {
            message += " not";
        }
        message += " be able to access the " + system + " when alert status is " + alertStatus;
        return message;
    }

    @Test
    @DisplayName("only allows bridge crew to access the the phasers")
    void canAccessPhasers() {
        assertAll(
                () -> assertTrue(testee.canAccessPhasers(picard),
                        "Bridge crew should be able to access phasers"),
                () -> assertFalse(testee.canAccessPhasers(barclay),
                        "Crew should not be able to access phasers"),
                () -> assertFalse(testee.canAccessPhasers(lwaxana),
                        "Non-crew should not be able to access phasers"),
                () -> assertFalse(testee.canAccessTransporter(q),
                        "User with null type should not be able to access phasers")
        );
    }
    */
}

