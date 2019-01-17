public class UnicodeSet {
    private String name;
    private int firstNumber;
    private int lastNumber;

    UnicodeSet(String setName, int setFirstNumber, int setLastNumber) {
        name = setName;
        firstNumber = setFirstNumber;
        lastNumber = setLastNumber;
    }

    String getName() {
        return name;
    }

    int getFirstNumber() {
        return firstNumber;
    }

    int getLastNumber() {
        return lastNumber;
    }
}
