package xj_adv.ch4_reflection.exercise421;

public class MagicClassInstantiatorTest {
    public static void main(String... args) throws Exception {
        MagicClassInstantiator
                .main(
                        "xj_adv.ch4_reflection.exercise421.MagicTestClass",
                        "howdie", "print");
        MagicClassInstantiator
                .main(
                        "xj_adv.ch4_reflection.exercise421.MagicTestClass",
                        "howdie", "there", "print");
        MagicClassInstantiator
                .main(
                        "xj_adv.ch4_reflection.exercise421.MagicTestClass",
                        "howdie", "there", "partner", "print");
        MagicClassInstantiator.main("java.lang.String", "heinz Kabutz",
                "toUpperCase");
        MagicClassInstantiator.main("java.util.ArrayList", "size");
        MagicClassInstantiator.main("java.util.ArrayList", "clear");
    }
}
