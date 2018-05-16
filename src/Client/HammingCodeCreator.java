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

    private String createHammingCode(String message){

        if(message.length()>=2) {

            char[] bytes = message.toCharArray();
            bytes = swapChars(bytes);

            int length = bytes.length;
            int maxChecker = 1;
            int checkersCount = 0;
            while (maxChecker < length+1) {
                length++;
                checkersCount++;
                maxChecker*=2;
            }
            char[] controlBits = createControlBits(bytes, checkersCount, bytes.length);

            return createFinalMessage(controlBits, bytes);
        }
        return null;
    }

    private char[] createControlBits(char[] bytes, int numOfCheckers, int messageLength){
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

            if(bytes[i] == '1'){
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

        char[] controlBitsValue = new char[numOfCheckers];

        int sumOfBits;
        for (int i = 0; i<numOfCheckers; i++){
            sumOfBits = 0;
            for(int j = 0; j<messageLength; j++){
                if(controlBitsPosition[j][i]=='1'){
                    sumOfBits++;
                }
            }
            sumOfBits%=2;
            if(sumOfBits==1){
                controlBitsValue[i] = '1';
            } else {
                controlBitsValue[i] = '0';
            }
        }

        return swapChars(controlBitsValue);
    }

    private char[] swapChars(char[] array) {

        char tempSwap;
        for (int i = 0; i < array.length/2; i++) {
            tempSwap = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = tempSwap;
        }
        return array;
    }

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
        for(int i=0; i<finalChars.length; i++){
            result = result + finalChars[i];
        }

        return result;
    }
}
