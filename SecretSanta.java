import java.util.*;
public class SecretSanta{

    private static void menu(){
        System.out.println(" MENU ");
        System.out.println("------");
        System.out.println("1. Add Participant");
        System.out.println("2. View Participants");
        System.out.println("3. Edit Participant Details");
        System.out.println("4. Remove Participant");
        System.out.println("5. Generate Secret Santa & Exit");
    }

    private static void toString(ArrayList<SecretSantaParticipant> p){
        // clear the terminal:
        System.out.println("\f");
        // print out list of members:
        for(int i= 0; i<p.size(); i++){
            System.out.println(i+":"+"\nname: "+p.get(i).name+"\nemail: "+p.get(i).email);
        }
    }

    public static void main(String [] args){
        // initialising scanners (2 diff scanners because of a bug in blueJ)
        Scanner s= new Scanner(System.in);
        Scanner sl= new Scanner(System.in);

        // creating and setting temp vars:
        int menuOpt= 0, edit= 0, sspSize= 0;
        boolean accept= true;

        // creating a list of secret santa participants:
        ArrayList<SecretSantaParticipant> ssp= new ArrayList<SecretSantaParticipant>();

        do{
            do{
                accept= true;

                // printing menu:
                menu();

                // getting menu choice & ensuring it is a valid option:
                try{
                    menuOpt= s.nextInt();
                }
                catch(InputMismatchException ex){
                    // make sure input is an int:
                    System.out.println("\f"+ex);
                    System.out.println("Please enter the number associated with your choice");
                    accept= false;
                    s.next();
                }

                // make sure input is a menu option:
                if(menuOpt<1 || menuOpt>5) {
                    System.out.println("That was not an option");
                    accept= false;
                }
            }while(!accept);

            switch(menuOpt){
                case 1:{
                        // Add Participant

                        // getting values:
                        System.out.print("\fEnter name: ");
                        String name= sl.nextLine();
                        System.out.print("Enter email address: ");
                        String email= s.next();

                        // adding new participant with those values:
                        ssp.add(new SecretSantaParticipant(name, email));
                    }
                    break;
                case 2:{
                        // View Participants

                        if(ssp.size()>0){

                            // print out list of members:
                            toString(ssp);

                            System.out.println("Press enter to return to main menu");
                            sl.nextLine();
                            System.out.println("\f");
                        }
                    }
                    break;
                case 3:{
                        // Edit Participant Details

                        do{
                            accept= true;

                            // print out list of members:
                            toString(ssp);
                            System.out.print("Enter which member you would like to edit: ");

                            // getting menu choice & ensuring it is a valid option:
                            try{
                                edit= s.nextInt();
                            }
                            catch(InputMismatchException ex){
                                // make sure input is an int:
                                System.out.println("\f"+ex);
                                System.out.println("Please enter the number associated with your choice");
                                accept= false;
                                sl.nextLine();
                            }
                            catch(IndexOutOfBoundsException ex){
                                // make sure input index given is a valid index
                                System.out.println("\f"+ex);
                                System.out.println("That index does not exist");
                                accept= false;
                                sl.nextLine();
                            }
                        }while(!accept);

                        // getting values:
                        System.out.print("\fEnter new name: ");
                        String name= sl.nextLine();
                        System.out.print("Enter new email address: ");
                        String email= s.next();

                        // adding new participant with those values:
                        ssp.set(edit, new SecretSantaParticipant(name, email));
                    }
                    break;
                case 4:{
                        // Remove Participant

                        do{
                            accept= true;

                            // print out list of members:
                            System.out.println("\f");
                            toString(ssp);

                            System.out.print("Enter which member you would like to remove: ");

                            // getting menu choice & ensuring it is a valid option:
                            try{
                                ssp.remove(s.nextInt());
                            }
                            catch(InputMismatchException ex){
                                // make sure input is an int:
                                System.out.println("\f"+ex);
                                System.out.println("Please enter the number associated with your choice");
                                accept= false;
                                sl.nextLine();
                            }
                            catch(IndexOutOfBoundsException ex){
                                // make sure input index given is a valid index
                                System.out.println("\f"+ex);
                                System.out.println("That index does not exist");
                                accept= false;
                                sl.nextLine();
                            }
                        }while(!accept);
                    }
                    break;
                case 5:{
                        // Genereate Secret Santa & Exit
                        // assumes that the user is not participating in the secret santa

                        /* generating secret santas:
                         *  rules for secret santa:
                         *  -CORE:
                         *    1. participants cannot have more than 1 secret santas [✔]
                         *    2. mulitple participants cannot have the same secret santa [✔]
                         *    3. participant cannot get themselves as a secret santa [✔]
                         */

                        System.out.println("Generating Secret Santa...");

                        // creating array with only the names:
                        String [] names= new String[ssp.size()];
                        for(int i= 0; i< ssp.size(); i++){
                            names[i]= ssp.get(i).name;
                        }

                        // creating a temp var to hold a random number:
                        int ran= -1;

                        // assigning names from the names array to the participants:
                        for(int i= 0; i< names.length; i++){

                            // generating the random numbers:
                            do{
                                ran= (int)(Math.random()*names.length);
                            }while(names[ran]== null || ran== i);
                            // the while conditions ensure that rule 2 & 3 are observed ⤴

                            // assigning random name to participant:
                            ssp.get(i).giftee= names[ran];
                            ssp.get(i).gifteemail= ssp.get(ran).email;
                            
                            // removing that entry in the names array to uphold rule 2:
                            names[ran]= null;
                        }

                        // notifying the user that the process has been completed:
                        System.out.println("Generation complete. Press enter to view results");
                        sl.nextLine();
                        // results are not show right away to let the user choose when to reveal the private information

                        //the toString method is not used since for this final output, another result must be displayed
                        System.out.println("\f");
                        for(int i= 0; i<ssp.size(); i++){
                            System.out.println(i+":"+"\nname: "+ssp.get(i).name+"\nemail: "+ssp.get(i).email+"\ngiftee: "+ssp.get(i).giftee+"\ngiftee's email: "+ssp.get(i).gifteemail);
                        }
                    }
                    break;
            }
        }while(menuOpt!= 5);
    }
}