package Client;

public class HammingCodeCreator {
    private String message;

    public HammingCodeCreator(String message) {
        this.message = createHammingCode(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = createHammingCode(message);
    }

    /**
     *
     * @param message
     * @return Hamming Code created from message
     */
    protected String createHammingCode(String message){

        if(message.length()>=1) {

            char[] bitMessage = message.toCharArray();
            bitMessage = swapChars(bitMessage);

            int length = bitMessage.length;
            int maxChecker = 1;
            int checkersCount = 0;
            while (maxChecker < length+1) {
                length++;
                checkersCount++;
                maxChecker*=2;
            }
            char[] controlBits = createControlBits(bitMessage, checkersCount, bitMessage.length);

            return createFinalMessage(controlBits, bitMessage);
        }
        return null;
    }

    /**
     * @param controlBits
     * @param message
     * @return proper fusion of controlBits and message
     */
    private String createFinalMessage(char[] controlBits, char[] message){
        char [] finalChars = new char[controlBits.length+message.length];

        int insertPosition = 0;
        for(int i = 0;i<controlBits.length;i++){
            if(i==0){
                finalChars[i] = controlBits[i];
            }else {
                insertPosition = insertPosition * 2 + 1;
                finalChars[insertPosition] = controlBits[i];
            }
        }

        insertPosition=3;
        for(int i = 0; i<message.length; i++){
            //check if insertPosition is pow of 2
            if ((insertPosition & (insertPosition - 1)) == 0)
                insertPosition++;
            finalChars[insertPosition++ -1] = message[i];
        }

        String result = "";
        for(int i=finalChars.length-1; i>=0; i--){
            result = result + finalChars[i];
        }

        return result;
    }

    /**
     *
     * @param message
     * @param numOfCheckers
     * @param messageLength
     * @return control bits created from message
     */
    private char[] createControlBits(char[] message, int numOfCheckers, int messageLength){
        char[][] controlBitsPosition = new char[messageLength][numOfCheckers];

        for (int i = 0; i<messageLength; i++){
            for(int j = 0; j<numOfCheckers; j++){
                controlBitsPosition[i][j]='0';
                }
            }

        int insertPosition=3;
        char [] binaryPosition;
        char [] temp = new char[numOfCheckers];
        for(int i = 0; i<temp.length; i++){
            temp[i] = '0';
        }
        for (int i = 0; i<messageLength; i++){
            //check if insertPosition is pow of 2
            if ((insertPosition & (insertPosition - 1)) == 0)
                insertPosition++;

            if(message[i] == '1'){
                binaryPosition = Integer.toString(convertToBinary(insertPosition)).toCharArray();
                for(int k = 0; k<binaryPosition.length; k++){
                    temp[temp.length -k -1] = binaryPosition[binaryPosition.length - k - 1];
                }

                for(int j = 0; j<temp.length; j++) {
                    controlBitsPosition[i][j] = temp[j];
                }
            }
            insertPosition++;
        }

        char[] controlBitsValue = sumBitsToChars(numOfCheckers, messageLength, controlBitsPosition);

        return swapChars(controlBitsValue);
    }

    /**
     *
     * @param numOfCheckers
     * @param messageLength
     * @param bitPositionOf1
     * @return char array, each element is sum of specific column
     */
    private char[] sumBitsToChars(int numOfCheckers, int messageLength, char[][] bitPositionOf1){
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
        return result;
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
