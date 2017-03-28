/**
 * Created by user on 3/25/2017.
 */
import java.lang.String;
import java.io.IOException;
import java.util.*;

public class MultipleSequenceAlignment {

    private static double [] F = new double[6480];


    private static String[][] sequences = new String[][]{
            {"A","A","T","T","A","T","G","G"},          //8
            {"A","C","A","T","T","G","T","T","G"},      //9
            {"G","C","C","A","G","G","A","G","G"},
            {"A","A","T","T","T","T","G","A","G","G"}   //10
    };

    public static void computeScore(){
        //====initialization========
        F[0] = 0;

        //====Computation===========
        for(int i =0; i<8;i++){
            for(int j =0; j<9; j++){
                for(int k =0; k<9; k++){
                    for(int l =0; l<10; l++){

                        if(i!=0 && j!=0 && k!=0 && l!=0) {
                            double[] S = MultipleSequenceAlignment.calculateSum(i, j, k, l); //compare the sequences starting from 0
                            double m = MultipleSequenceAlignment.findMax(S, i, j, k, l);
                            F[10 * (9 * (9 * i + j) + k) + l] = m;
                        }
                    }
                }
            }
        }

        //====Termination==========
        System.out.println(F[6479]);
    }

    // Return a list of sum
    public static double[] calculateSum(int i, int j, int k, int l){

        double [] S = new double[15];

        String x_1 = sequences[0][i];
        String x_2 = sequences[1][j];
        String x_3 = sequences[2][k];
        String x_4 = sequences[3][l];

        S[0] = MultipleSequenceAlignment.sumOfPairs(x_1, x_2, x_3, x_4);
        S[1] = MultipleSequenceAlignment.sumOfPairs("_", x_2, x_3, x_4);
        S[2] = MultipleSequenceAlignment.sumOfPairs(x_1, "_", x_3, x_4);
        S[3] = MultipleSequenceAlignment.sumOfPairs(x_1, x_2, "_", x_4);
        S[4] = MultipleSequenceAlignment.sumOfPairs(x_1, x_2, x_3, "_");
        S[5] = MultipleSequenceAlignment.sumOfPairs("_", "_", x_3, x_4);
        S[6] = MultipleSequenceAlignment.sumOfPairs(x_1, "_", "_", x_4);
        S[7] = MultipleSequenceAlignment.sumOfPairs(x_1, x_2, "_", "_");
        S[8] = MultipleSequenceAlignment.sumOfPairs("_", "_", "_", x_4);
        S[9] = MultipleSequenceAlignment.sumOfPairs(x_1, "_", "_", "_");
        S[10] = MultipleSequenceAlignment.sumOfPairs("_", x_2, "_", "_");
        S[11] = MultipleSequenceAlignment.sumOfPairs("_", "_", x_3, "_");
        S[12] = MultipleSequenceAlignment.sumOfPairs("_", x_2, x_3, "_");
        S[13] = MultipleSequenceAlignment.sumOfPairs("_", x_2, "_", x_4);
        S[14] = MultipleSequenceAlignment.sumOfPairs(x_1, "_", x_3, "_");

        return S;
    }

    //Return S(x1, x2, x3, x4)
    public static double sumOfPairs(String x1, String x2, String x3, String x4){
        double[] s = new double [6];

            if(x1.equals(x2)){
                s[0] = 3;
            }else if(x1.equals("_")||x2.equals("_")){
                s[0] = -1.5;
            }else{
                s[0] = -2;
            }

            if(x1.equals(x3)){
                s[1] = 3;
            }else if(x1.equals("_")||x3.equals("_")){
                s[1] = -1.5;
            }else{
                s[1] = -2;
            }

        if(x1.equals(x4)){
            s[2] = 3;
        }else if(x1.equals("_")||x4.equals("_")){
            s[2] = -1.5;
        }else{
            s[2] = -2;
        }

        if(x2.equals(x3)){
            s[3] = 3;
        }else if(x2.equals("_")||x3.equals("_")){
            s[3] = -1.5;
        }else{
            s[3] = -2;
        }

        if(x2.equals(x4)){
            s[4] = 3;
        }else if(x2.equals("_")||x4.equals("_")){
            s[4] = -1.5;
        }else{
            s[4] = -2;
        }

        if(x3.equals(x4)){
            s[5] = 3;
        }else if(x3.equals("_")||x4.equals("_")){
            s[5] = -1.5;
        }else{
            s[5] = -2;
        }

        double sum = 0;

        for(int i =0; i<6; i++){
            sum+=s[i];
        }
        return sum;

    }

    //Return max after calculation and comparison
    public static double findMax(double[] S, int i, int j, int k, int l){

        double[] FS = new double[15];
        if(i!=0 && j!=0 && k!=0 && l!=0) {
            FS[0] = F[10 * (9 * (9 * (i - 1) + (j - 1)) + (k - 1)) + (l - 1)] + S[0];
        }
        if(j!=0 && k!=0 && l!=0) {
            FS[1] = F[10 * (9 * (9 * i + (j - 1)) + (k - 1)) + (l - 1)] + S[1];
        }
        if(i!=0 && k!=0 && l!=0) {
            FS[2] = F[10 * (9 * (9 * (i - 1) + j) + (k - 1)) + (l - 1)] + S[2];
        }
        if(j!=0 && i!=0 && l!=0) {
            FS[3] = F[10 * (9 * (9 * (i - 1) + (j - 1)) + k) + (l - 1)] + S[3];
        }
        if(j!=0 && k!=0 && i!=0) {
            FS[4] = F[10 * (9 * (9 * (i - 1) + (j - 1)) + (k - 1)) + l] + S[4];
        }
        if(k!=0 && l!=0) {
            FS[5] = F[10 * (9 * (9 * i + j) + (k - 1)) + (l - 1)] + S[5];
        }
        if(i!=0 && l!=0) {
            FS[6] = F[10 * (9 * (9 * (i - 1) + j) + k) + (l - 1)] + S[6];
        }
        if(i!=0 && j!=0) {
            FS[7] = F[10 * (9 * (9 * (i - 1) + (j - 1)) + k) + l] + S[7];
        }
        if(l!=0) {
            FS[8] = F[10 * (9 * (9 * i + j) + k) + (l - 1)] + S[8];
        }
        if(i!=0) {
            FS[9] = F[10 * (9 * (9 * (i - 1) + j) + k) + l] + S[9];
        }
        if(j!=0) {
            FS[10] = F[10 * (9 * (9 * i + (j - 1)) + k) + l] + S[10];
        }
        if(k!=0) {
            FS[11] = F[10 * (9 * (9 * i + j) + (k - 1)) + l] + S[11];
        }
        if(k!=0 && j!=0) {
            FS[12] = F[10 * (9 * (9 * i + (j - 1)) + (k - 1)) + l] + S[12];
        }
        if(l!=0 && j!=0) {
            FS[13] = F[10 * (9 * (9 * i + (j - 1)) + k) + (l - 1)] + S[13];
        }
        if(i!=0 && k!=0) {
            FS[14] = F[10 * (9 * (9 * (i - 1) + j) + (k - 1)) + l] + S[14];
        }

        double f =Double.NEGATIVE_INFINITY;
        for(int w=0; w<15; w++){
            if(FS[w]>f){
                f = FS[w];
            }
        }
        return f;
    }

    public static void main(String[]args){

        MultipleSequenceAlignment msa = new MultipleSequenceAlignment();

        msa.computeScore();
    }
}
