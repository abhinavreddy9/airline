import java.util.Random;
import java.util.Scanner;
class Queue
{
    int arr[];
    int maxcapacity,size,start,end;


    // implementing the q and setting the values for start and end of the q
    public Queue(int maxcapacity) {
        this.maxcapacity = maxcapacity;
        start = this.size = 0;
        end = maxcapacity - 1;
        arr = new int[this.maxcapacity];

    }


    // Setting Queue to empty when size is 0
    boolean isEmpty(Queue q)
    { return (q.size == 0); }

    // Setting Queue to full when the size is equal to maxcapacity
    boolean isFull(Queue q)
    { return (q.size == q.maxcapacity);
    }


    // Adding an item to the queue(enqueuing)
    void enqueue(int item)
    {
        if (isFull(this))
            return;
        this.end = (this.end + 1)%this.maxcapacity;
        this.arr[this.end] = item;
        this.size = this.size + 1;
    }

    // Removing an item to the queue(dequeuing)
    int dequeue()
    {
        if (isEmpty(this)) {
            return Integer.MIN_VALUE;
        }
        int item = this.arr[this.start];
        this.start = (this.start + 1)%this.maxcapacity;
        this.size = this.size - 1;
        return item;
    }

    int sum()
    {
        if (isEmpty(this)) {
            return Integer.MIN_VALUE;
        }
        int sum1=0;
        for(int i=start;i<=end;i++) {
            sum1+=arr[i];
        }
        return sum1;
    }

    int start()             // obtaining the first element of the queue
    {
        if (isEmpty(this)) {
            return Integer.MIN_VALUE;
        }
        return this.arr[this.start];
    }

    int end()             // obtaining the last element of the queue
    {
        if (isEmpty(this)) {
            return Integer.MIN_VALUE;
        }
        return this.arr[this.end];
    }
}

public class Airline
{
    //method to return a random number whose average is n
    static int random(int n)
    {
        Random rand = new Random();
        int a = rand.nextInt(2*n)+1;
        return a;
    }


    public static void main(String[] args)
    {
        int checkin,coach_Arrival_Rate,coach_Service_Rate,first_Arrival_Rate,first_Service_Rate;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the duration of Checkin ");
        checkin = sc.nextInt();
        System.out.println("Enter the average Coach Arrival Rate ");
        coach_Arrival_Rate= sc.nextInt();
        System.out.println("Enter the Average Coach Service Rate ");
        coach_Service_Rate= sc.nextInt();
        System.out.println("Enter the Average First Class arrival Rate ");
        first_Arrival_Rate= sc.nextInt();
        System.out.println("Enter the Average First Class Service Rate \n");
        first_Service_Rate= sc.nextInt();

        int fcs1=0,fcs2=0,cs1=0,cs2=0,cs3=0; // fcs1,fcs2 are first class service stations
        // cs1,cs2,cs3 are coach service station

        //count for the stations that were busy
        int cfcs1=0,cfcs2=0,ccs1=0,ccs2=0,ccs3=0;

        //waiting time count for coach queue and first class queue
        int waitTime1=0,waitTime2=0;

        //maximum waiting time for coach q and first class queue
        int maxWaitTime1=0,maxWaitTime2=0;

        //count for which kind of passenger
        int countCoachPassanger=0,countFirstPassanger=0;


        Queue Coachq = new Queue(100);

        Queue Firstq = new Queue(100);

        int i;
        int maxlength1=0,maxlength2=0;
        int coacharrivaltime,firstarrivaltime;
        int lastarrt1=0,lastarrt2=0;

        for(i=0;i<=checkin;i++)
        {
            int ar1,ar2;

            if(lastarrt1>0) {
                lastarrt1--;
            }
            if(lastarrt2>0) {
                lastarrt2--;
            }

            if(cs1!=0)
                cs1--;
            if(cs2!=0)
                cs2--;
            if(cs3!=0)
                cs3--;
            if(fcs1!=0)
                fcs1--;
            if(fcs2!=0)
                fcs2--;

            if(lastarrt2==0 && i<=checkin)
            {
                firstarrivaltime=random(first_Arrival_Rate);
                ar2=random(first_Service_Rate);
                Firstq.enqueue(ar2);
                lastarrt2=firstarrivaltime;
                countFirstPassanger+=1;             }

            //sending first class passengers to service stations
            if(fcs1==0 && Firstq.isEmpty(Firstq)==false) // checking if station is empty and queue is not empty
            {
                fcs1=Firstq.dequeue();
                cfcs1+=fcs1;
            }
            if(fcs2==0 && Firstq.isEmpty(Firstq)==false)
            {
                fcs2=Firstq.dequeue();;
                cfcs2+=fcs2;
            }


            if(lastarrt1==0  && i<=checkin) //if the lastarr1 time is zero new passenger's arrival time will be calculated
            {
                coacharrivaltime=random(coach_Arrival_Rate);
                ar1=random(coach_Service_Rate);
                Coachq.enqueue(ar1);
                lastarrt1=coacharrivaltime;
                countCoachPassanger+=1;
            }

            if(cs1==0 && Coachq.isEmpty(Coachq)==false)
            {
                cs1=Coachq.dequeue();
                ccs1+=cs1;
            }

            if(cs2==0 && Coachq.isEmpty(Coachq)==false)
            {
                cs2=Coachq.dequeue();
                ccs2+=cs2;
            }
            if(cs3==0 && Coachq.isEmpty(Coachq)==false)
            {
                cs3=Coachq.dequeue();
                ccs3+=cs3;

            }
            if(fcs1==0 && Coachq.isEmpty(Coachq)==false)
            {
                fcs1=Coachq.dequeue();
                cfcs1+=fcs1;

            }
            if(fcs2==0 && Coachq.isEmpty(Coachq)==false)
            {
                fcs2=Coachq.dequeue();
                cfcs2+=fcs2;
            }

            if(fcs1==0 && fcs2==0 && cs1==0 && cs2==0 && cs3==0 && i>checkin)
                break;

            // Calculating the total waiting time and maximum wait time for coach passenger
            if(Coachq.isEmpty(Coachq)==false)
            {
                waitTime1+=Coachq.size;
                if (maxlength1<Coachq.size)
                    maxlength1=Coachq.size;
                if (maxWaitTime1<Coachq.sum())
                    maxWaitTime1=Coachq.sum();
            }

            // Calculating the total waiting time and maximum waiting time for First class passenger
            if(Firstq.isEmpty(Firstq)==false)
            {
                waitTime2+=Firstq.size;
                if(maxlength2<Firstq.size)
                    maxlength2=Firstq.size;
                if(maxWaitTime2<Firstq.sum())
                    maxWaitTime2=Firstq.sum();
            }
        }


        float avgwaitTime1= (float)waitTime1/countCoachPassanger;
        float avgwaitTime2= (float)waitTime2/countFirstPassanger;

        System.out.println("The duration of the simulation is "+i+ " minutes");
        System.out.println("Max length of Coach queue is "+maxlength1);
        System.out.println("Max length of First class queue is "+maxlength2);
        System.out.println("Max wait time for Coach queue is "+maxWaitTime1+" minutes");
        System.out.println("Max wait time for First class queue is "+maxWaitTime2+" minutes");
        System.out.printf("Average wait time for Coach queue is %.2f min\n",avgwaitTime1);
        System.out.printf("Average wait time for First class queue is %.2f min\n",avgwaitTime2);
        System.out.println("Percentage of time Coach Service Station cs1 was busy "+(ccs1*100/(i))+"%");
        System.out.println("Percentage of time Coach Service Station cs2 was busy "+(ccs2*100/(i))+"%");
        System.out.println("Percentage of time Coach Service Station cs3 was busy "+(ccs3*100/(i))+"%");
        System.out.println("Percentage of time First Class Service station fcs1 was busy "+(cfcs1*100/(i))+"%");
        System.out.println("Percentage of time First Class Service station fcs2 was busy "+(cfcs2*100/(i))+"%");
    }
}