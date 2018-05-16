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
     * @param message - String with bits as Hamming Code
     * @return -1 when message is to short
     * @return 0 when message is corrent
     * @return int that shows in which position (not index) there is an error
     */
    public int correctMessage(String message){

        if(message.length()>=3) {
            char[] bitMessage = message.toCharArray();
            bitMessage = swapChars(bitMessage);

            int length = bitMessage.length;
            int checkersCount = 0;
            int maxChecker = 1;
            while (maxChecker < length) {
                checkersCount++;
                maxChecker *= 2;
            }

            return validateMessage(bitMessage, checkersCount, bitMessage.length);
        }
        return -1;
    }

    /**
     *
     * @param message
     * @param numOfCheckers
     * @param messageLength
     * @return int with result of validation
     */
    private int validateMessage(char[] message, int numOfCheckers, int messageLength) {
        char[][] bitPositionOf1 = new char[messageLength][numOfCheckers];

        for (int i = 0; i<messageLength; i++){
            for(int j = 0; j<numOfCheckers; j++){
                bitPositionOf1[i][j]='0';
            }
        }

        char [] binaryPosition;
        char [] temp = new char[numOfCheckers];
        for(int i = 0; i<temp.length; i++){
            temp[i] = '0';
        }
        for (int i = 0; i<messageLength; i++){
            if(message[i] == '1'){
                binaryPosition = Integer.toString(convertToBinary(i+1)).toCharArray();
                for(int k = 0; k<binaryPosition.length; k++){
                    temp[temp.length -k -1] = binaryPosition[binaryPosition.length - k - 1];
                }

                for(int j = 0; j<temp.length; j++) {
                    bitPositionOf1[i][j] = temp[j];
                }
            }
        }

        return sumBitsToDecimal(numOfCheckers, messageLength, bitPositionOf1);
    }

    /**
     *
     * @param numOfCheckers
     * @param messageLength
     * @param bitPositionOf1
     * @return result of adding binary columns in matrix in decimal
     */
    private int sumBitsToDecimal(int numOfCheckers, int messageLength, char[][] bitPositionOf1){
        char[] result = new char[numOfCheckers];

        int sumOfBits;
        for (int i = 0; i<numOfCheckers; i++){
            sumOfBits = 0;
            for(int j = 0; j<messageLength; j++){
                if(bitPositionOf1[j][i]=='1'){
                    sumOfBits++;
                }
            }
            sumOfBits%=2;
            if(sumOfBits==1){
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
        for (int i = 0; i < array.length/2; i++) {
            tempSwap = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = tempSwap;
        }
        return array;
    }

    /**
     *
     * @param binaryNumber
     * @return decimalNumber
     */
    private int convertToDecimal(char[] binaryNumber) {
        int binary;
        //binaryNumber = swapChars(binaryNumber);
        String tempString ="";
        for(int i = 0; i<binaryNumber.length; i++){
            tempString = tempString + binaryNumber[i];
        }
        binary = Integer.valueOf(tempString);

        int result = 0;
        int multiplier = 1;

        while(binary > 0){
            int residue = binary % 10;
            binary     = binary / 10;
            result      = result + residue * multiplier;
            multiplier  = multiplier * 2;
        }

        System.out.println("whole result:");
        return result;
    }

    /**
     *
     * @param decimal
     * @return binaryNumber
     */
    private int convertToBinary(int decimal)
    {
        int result = 0;
        int multiplier = 1;

        while(decimal > 0){
            int residue = decimal % 2;
            decimal     = decimal / 2;
            result      = result + residue * multiplier;
            multiplier  = multiplier * 10;
        }

        return result;
    }
}
