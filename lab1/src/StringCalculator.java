import java.util.ArrayList;

public class StringCalculator {
    public int add(String numbers){
        int sum =0;
        short i =0;

        ArrayList<String> delimetrs = new ArrayList<>();
        ArrayList<String> number = new ArrayList<>();
        StringBuilder negative_num = new StringBuilder();
        String[] new_str;

        if(numbers.contains("//")){
            new_str =numbers.split("//|\\\\n");
        }
        else{
            new_str = numbers.split(" ");
        }

        for(String s : new_str){
            if (s.equals("*") || s.contains("[*]")){
                delimetrs.add("\\"+s);

            }
            else if( i == 1){
                delimetrs.add(s);
            }
            else{
                number.add(s);
            }
            i++;
        }

        String patern="";

        if (!delimetrs.isEmpty()){
            if (delimetrs.get(0).contains("]")){
                String[] new_del = delimetrs.get(0).split("\\[");
                for(String s : new_del){

                    patern+=s.replace("]","|");
                }
                numbers = number.get(1);
                patern+="\\\\n|,+";
            }
            else{
                numbers = number.get(1);
                patern+=delimetrs.get(0)+"|"+"\\\\n|,+";
            }
        }
        else{
            numbers = number.get(0);
            patern+="\\\\n|,+";
        }

        new_str = numbers.split(patern);

        for (String s : new_str) {
            if(s.isEmpty()){
                s = "0";
            }
            if (Integer.parseInt(s) < 1001) {
                sum += Integer.parseInt(s);
            }
            if (Short.parseShort(s) < 0) {
                negative_num.append(" ").append(s);
            }
        }
        if (negative_num.length() > 0) {
            System.out.printf("Error!Input contains negative numbers:%s \n", negative_num);
        }
        else{
            System.out.println(sum);
        }
    return 0;}
}
