package Server;

class ServerHammingCode {

    private String message;

    ServerHammingCode(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }

    /**
     * @return int that shows in which position (not index) there is an error
     */
    public int findErrorInMessage() {

        int errorNumber;
        if (message.length() >= 3) {
            char[] bitMessage = message.toCharArray();
            bitMessage = swapChars(bitMessage);

            int length = bitMessage.length;
            int checkersCount = 0;
            int maxChecker = 1;
            while (maxChecker < length) {
                checkersCount++;
                maxChecker *= 2;
            }
            errorNumber = validateMessage(bitMessage, checkersCount, bitMessage.length);
            correctMessage(errorNumber);
            return errorNumber;
        }
        return -1;
    }

    private void correctMessage(int errorNumber) {
        System.out.println(errorNumber);
        if (errorNumber == -1) {
            message = "Message is too short!";
        }
        if (errorNumber > 0) {

            char[] bits = message.toCharArray();

            if (bits[bits.length - errorNumber] == '1') {
                bits[bits.length - errorNumber] = '0';
            } else {
                bits[bits.length - errorNumber] = '1';
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(bits);
            message = stringBuilder.toString();
        }
    }

    /**
     * @param message
     * @param numOfCheckers
     * @param messageLength
     * @return int with result of validation
     */
    private int validateMessage(char[] message, int numOfCheckers, int messageLength) {
        char[][] bitPositionOf1 = new char[messageLength][numOfCheckers];

        for (int i = 0; i < messageLength; i++) {
            for (int j = 0; j < numOfCheckers; j++) {
                bitPositionOf1[i][j] = '0';
            }
        }

        char[] binaryPosition;
        char[] temp = new char[numOfCheckers];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = '0';
        }
        for (int i = 0; i < messageLength; i++) {
            if (message[i] == '1') {
                binaryPosition = Integer.toString(convertToBinary(i + 1)).toCharArray();
                for (int k = 0; k < binaryPosition.length; k++) {
                    temp[temp.length - k - 1] = binaryPosition[binaryPosition.length - k - 1];
                }

                for (int j = 0; j < temp.length; j++) {
                    bitPositionOf1[i][j] = temp[j];
                }
            }
        }

        return sumBitsToDecimal(numOfCheckers, messageLength, bitPositionOf1);
    }

    /**
     * @param numOfCheckers
     * @param messageLength
     * @param bitPositionOf1
     * @return result of adding binary columns in matrix in decimal
     */
    private int sumBitsToDecimal(int numOfCheckers, int messageLength, char[][] bitPositionOf1) {
        char[] result = new char[numOfCheckers];

        int sumOfBits;
        for (int i = 0; i < numOfCheckers; i++) {
            sumOfBits = 0;
            for (int j = 0; j < messageLength; j++) {
                if (bitPositionOf1[j][i] == '1') {
                    sumOfBits++;
                }
            }
            sumOfBits %= 2;
            if (sumOfBits == 1) {
                result[i] = '1';
            } else {
                result[i] = '0';
            }
        }
        return convertToDecimal(result);
    }

    /**
     * @param array - char array
     * @return swapped char array
     */
    private char[] swapChars(char[] array) {
        char tempSwap;
        for (int i = 0; i < array.length / 2; i++) {
            tempSwap = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = tempSwap;
        }
        return array;
    }

    /**
     * @param binaryNumber
     * @return decimalNumber
     */
    private int convertToDecimal(char[] binaryNumber) {
        int binary;
        //binaryNumber = swapChars(binaryNumber);
        String tempString = "";
        for (int i = 0; i < binaryNumber.length; i++) {
            tempString = tempString + binaryNumber[i];
        }
        binary = Integer.valueOf(tempString);

        int result = 0;
        int multiplier = 1;

        while (binary > 0) {
            int residue = binary % 10;
            binary = binary / 10;
            result = result + residue * multiplier;
            multiplier = multiplier * 2;
        }

        System.out.println("whole result:");
        return result;
    }

    /**
     * @param decimal
     * @return binaryNumber
     */
    private int convertToBinary(int decimal) {
        int result = 0;
        int multiplier = 1;

        while (decimal > 0) {
            int residue = decimal % 2;
            decimal = decimal / 2;
            result = result + residue * multiplier;
            multiplier = multiplier * 10;
        }

        return result;
    }
}
