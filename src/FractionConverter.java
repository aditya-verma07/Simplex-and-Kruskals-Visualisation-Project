import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class FractionConverter {
    public static String toFraction(double decimal) {
        BigDecimal decimalBig = new BigDecimal(Double.toString(decimal)).setScale(13, RoundingMode.HALF_UP);
        String decimalString = decimalBig.toPlainString(); // Convert to string representation
        String[] parts = decimalString.split("\\."); // Split into integer and fractional parts
        String integerPart = parts[0];
        String fractionalPart = parts.length > 1 ? parts[1] : ""; // Handle case where there is no fractional part

        // Check for repeating decimals
        for (int i = 1; i <= fractionalPart.length() / 2; i++) {
            String repeat = fractionalPart.substring(i); // Start checking from the current index
            String nonRepeatingPart = fractionalPart.substring(0, i); // Get the non-repeating part

            // Check if the repeating part is indeed repeating
            if (repeat.startsWith(nonRepeatingPart) && repeat.length() % nonRepeatingPart.length() == 0) {
                return recurringDecimal(integerPart, fractionalPart, nonRepeatingPart);
            }
        }

        return terminatingDecimal(decimalString); // Treat as a regular terminating decimal
    }

    private static String recurringDecimal(String integerPart, String fractionalPart, String repeat) {
        // Find the length of the non-repeating part
        int nonRepeatingLength = fractionalPart.indexOf(repeat);
        String nonRepeatingPart = fractionalPart.substring(0, nonRepeatingLength); // Non-repeating part

        // Combine the non-repeating and repeating parts
        String fullDecimal = nonRepeatingPart + repeat;

        // Calculate the numerator
        BigInteger repeatingNumerator = new BigInteger(fullDecimal);
        BigInteger nonRepeatingNumerator = nonRepeatingPart.isEmpty() ? BigInteger.ZERO : new BigInteger(nonRepeatingPart);

        // The numerator is the repeating part minus the non-repeating part
        BigInteger numerator = repeatingNumerator.subtract(nonRepeatingNumerator);

        // Calculate the denominator
        BigInteger denominator = BigInteger.TEN.pow(nonRepeatingLength + repeat.length())
                .subtract(BigInteger.TEN.pow(nonRepeatingLength));

        // Add the integer part to the numerator
        BigInteger integerPartValue = new BigInteger(integerPart);
        numerator = numerator.add(integerPartValue.multiply(denominator));

        // Simplify the fraction
        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);

        return numerator + "/" + denominator;
    }

    private static String terminatingDecimal(String decimalString) {
        BigDecimal decimal = new BigDecimal(decimalString); //using BigDecimal for improved accuracy
        int scale = decimal.scale(); //number of digits after the decimal point
        BigInteger numerator = decimal.movePointRight(scale).toBigInteger(); //shift the decimal point right by the scale
        BigInteger denominator = BigInteger.TEN.pow(scale);

        BigInteger gcd = numerator.gcd(denominator); //simplifies the fraction using the greatest common denominator
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);

        return numerator + "/" + denominator;
    }

    public static void main(String[] args) {
        // Test cases
        System.out.println(toFraction(0.1272727272727));
        /*System.out.println(toFraction(0.111111));
        System.out.println(toFraction(0.128712871287));
        System.out.println(toFraction(0.300000000000000012));*/
    }
}
