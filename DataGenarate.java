package src;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenarate {

    static int next_int(int a, int b, Random rand) {
        //inclusive on both side

        return rand.nextInt(b - a + 1) + a;
    }

    public static void main(String[] args) throws IOException {
        int ins_num = 15;
        Random rand = new Random(666);
        if (args.length != 0) {
            ins_num = Integer.getInteger(args[0]);
        }
        int  Gate_size = 2;
        double gate_time_gap = 0.5; // 0.5min time gap between gap,
        int Flight_Type_Size = 3; // come 1/4; go 1/4; come and go 1/2.
        int Baggage_LB[] = {8, 5, 2};
        int Baggage_UB[] = {10, 7, 4};
        int TimeWindow_LB[] = {90, 60, 40};
        int TimeWindow_UB[] = {120, 90, 60};


        int Gate_Density_LB = 6;
        int Gate_Density_UB = 10;
        int Time_Gap_LB = 20;
        int Time_Gap_UB = 120;


        //            int Necessary_Time_Slack = 5;
        int Unit_Service_Time = 3;// c_time
        int max_dollies_per_tug = 6; // d_lim
        int max_tugs_per_flight = 4; // m
        int max_dollies_at_gate = 10; // n
        int max_duration = 60; // flight.etime -> depot, g_time
        double speed = 0.3; // 0.3km/min
        double tug_cost = 20; //  20 S$/tug
        double travel_cost = 0.05; //  0.00005 S$/km


        for (int gate_size = 1; gate_size <= Gate_size ; gate_size++) {
            for (int inst = 1; inst <= ins_num; inst++) {
//                File file = new File("./data/AP"+gate_size+"_" + inst + ".txt");
                File file = new File("./data_1/AP" + gate_size + "_" + inst + ".txt");
                BufferedWriter out = new BufferedWriter(new FileWriter(file));


//            System.out.println("Gate_Gap: " + gate_gap);
//            System.out.println("Necessary_Time_Slack: " + Necessary_Time_Slack);
                System.out.println("Unit_Service_Time(min): " + Unit_Service_Time);
                System.out.println("max_dollies_per_tug : " + max_dollies_per_tug);
                System.out.println("max_tugs_per_flight : " + max_tugs_per_flight);
                System.out.println("max_dollies_at_gate : " + max_dollies_at_gate);
                System.out.println("max_duration(min): " + max_duration);
                System.out.println("speed(km/min): " + speed);
                System.out.println("tug_cost(S$/tug): " + tug_cost);
                System.out.println("travel_cost(S$/m): " + travel_cost);

                out.write("Unit_Service_Time(min): " + Unit_Service_Time + "\r\n");
                out.write("max_dollies_per_tug: " + max_dollies_per_tug + "\r\n");
                out.write("max_tugs_per_flight: " + max_tugs_per_flight + "\r\n");
                out.write("max_dollies_at_gate: " + max_dollies_at_gate + "\r\n");
                out.write("max_duration(min): " + max_duration + "\r\n");
                out.write("speed(km/s): " + speed + "\r\n");
                out.write("tug_cost(S$/tug): " + tug_cost + "\r\n");
                out.write("travel_cost(S$/km): " + travel_cost + "\r\n");


                int[] l = new int[gate_size];
                for (int i = 0; i < gate_size; i++) {
                    l[i] = i + 1;
                    // l[i] = next_int(0, gate_size);
                }
                System.out.println("Gate_Size: " + gate_size);
                out.write("Gate_Size: " + gate_size + "\r\n");
                for (int i = 0; i < l.length; i++) {
                    System.out.print(l[i] + " ");
                    out.write(l[i] + " ");
                }


                System.out.println("\r\ntravel time Matrix: ");
                out.write("\r\ntravel time Matrix: \r\n");
                int[] pos = new int[gate_size + 1];
                for (int i = 1; i <= gate_size; i++)
                    pos[l[i - 1]] = i;
                for (int i = 0; i <= gate_size; i++) {
                    for (int j = 0; j <= gate_size; j++) {
                        System.out.print(gate_time_gap * Math.abs(pos[i] - pos[j]) + " ");
                        out.write(gate_time_gap * Math.abs(pos[i] - pos[j]) + " ");
                    }
                    System.out.println();
                    out.write("\r\n");
                }

                int Total_Flight = 0;
                // number of flight at each gate
                int[] p = new int[gate_size];
                for (int i = 0; i < gate_size; i++) {
                    p[i] = next_int(Gate_Density_LB, Gate_Density_UB, rand);
                    Total_Flight += p[i];
                }
                System.out.println("Total_Flight_Number: " + Total_Flight);
                out.write("Total_Flight_Number: " + Total_Flight);

                int flightID = 0;
                for (int i = 0; i < gate_size; i++) {
                    int sz = p[i];
                    int ct = 0;
                    for (int j = 0; j < sz; j++) {
                        ++flightID;
                        ct += next_int(Time_Gap_LB, Time_Gap_UB, rand);
                        int ty = next_int(0, Flight_Type_Size - 1, rand);
//                        System.out.print("Gate_ID: " + (i + 1) + " ");
//                        System.out.print("Flight_ID: " + flightID + " ");
//                        System.out.print("Time_Window: " + ct + " ");
                        out.write("\r\nGate_ID: " + (i + 1) + " ");
                        out.write("Flight_ID: " + flightID + " ");
                        out.write("Time_Window: " + ct + " ");
                        ct += next_int(TimeWindow_LB[ty], TimeWindow_UB[ty], rand);
//                        System.out.print(ct + " ");
                        out.write(ct + " ");
                        int bty = next_int(0, 2, rand);
//                        System.out.print("Pickup_Baggage: " + next_int(Baggage_LB[bty], Baggage_UB[bty], rand) + " ");
//                        System.out.print("Delivery_Baggage: " + next_int(Baggage_LB[bty], Baggage_UB[bty], rand) + " ");
//                        System.out.println();
                        int pick = next_int(Baggage_LB[ty], Baggage_UB[ty], rand);
                        int delivery = next_int(Baggage_LB[bty], Baggage_UB[bty], rand);
                        bty = next_int(0, 3, rand);
                        if (bty == 0) {
                            pick = 0;
                        }
                        if (bty == 3 && pick != 0) {
                            delivery = 0;
                        }
                        out.write("Pickup_Baggage: " + pick + " ");
                        out.write("Delivery_Baggage: " + delivery + " ");

                    }
                    ct += next_int(Time_Gap_LB, Time_Gap_UB, rand);

                }
                out.flush(); // 把缓存区内容压入文件
                out.close(); // 最后记得关闭文件

            }
        }
    }
}
