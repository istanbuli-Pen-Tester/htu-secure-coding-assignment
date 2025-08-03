package finalSC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
//all passwords are 1324
//record keeper name is RK
//there is only 1 doctor and 1 patient in the system 
public class  PatientAndDoctorInfo{
	 private static String hashPassword(String password) {
	        String hashedPassword = "";
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
	            hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println("The algorithm does not exist");
	        }
	        return hashedPassword;
	    }

    public static void main(String[] args) {
        try {
    	PatientAndDoctorInfo app = new PatientAndDoctorInfo();
        Scanner sc = new Scanner(System.in);
        
        
        System.out.println("Enter username:");
        String username = sc.next();
        System.out.println("Enter password:");
        String password = sc.next();
        int userType = app.login(username, password);
        switch (userType) {
        case 1:

        	int choice1=0;  
        	while(choice1 !=4) {
        	System.out.println("Choose an option:");
            System.out.println("1. Enter Patient Information");
            System.out.println("2. Enter Doctor Information");
            System.out.println("3. View Information");
            System.out.println("4. Exit");
            choice1=sc.nextInt();
            if (choice1 == 1) {
            	app.enterPatientInfo();
            	choice1=4;
            }
            if (choice1 == 2) {
            	app.enterDoctorInfo();
            }
            if (choice1 == 3) {
            	app.viewInformation();
            }
        	}
        	break;
        case 2:
        	int choice2 =0; 
        	while(choice2 !=3) {
        	System.out.println("Choose an option:");
            System.out.println("1. Enter Patient Information");
            System.out.println("2. View Information");
            System.out.println("3. Exit");
            choice2 = sc.nextInt();
            if (choice2 == 1) {
            	app.enterPatientInfo();
            }
            if (choice2 == 2) {
            	app.viewPatientInformationWrapper();
            }
            
        	}
        	break;
        case 3:
        	int choice3 =0; 
        	while(choice3 !=2) {
        	System.out.println("Choose an option:");
            System.out.println("1. View Information");
            System.out.println("2. Exit");
            choice3 =sc.nextInt();
            if (choice3 ==1) {
            	app.viewOwnPatientInformation(username);
            }
        	}
        	break;
        }
    
        sc.close();
    }catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
        e.printStackTrace();
    }
    }

    public int login(String name, String password) {
    	password = hashPassword(password);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("recordKeeperInformation.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split(",");
                if (str[0].equals(name) && str[1].equals(password)) {
                    System.out.println("Logged in as Record Keeper.");
                    return 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading record keeper information: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing BufferedReader: " + e.getMessage());
                }
            }
        }

        try {
            br = new BufferedReader(new FileReader("doctorInformation.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split(",");
                if (str[0].equals(name) && str[1].equals(password)) {
                    System.out.println("Logged in as Doctor.");
                    return 2;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading doctor information: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing BufferedReader: " + e.getMessage());
                }
            }
        }

        try {
            br = new BufferedReader(new FileReader("patientInformation.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split(",");
                if (str[0].equals(name) && str[1].equals(password)) {
                    System.out.println("Logged in as Patient.");
                    return 3;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading patient information: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing BufferedReader: " + e.getMessage());
                }
            }
        }

        System.out.println("Login failed. Invalid username or password.");
        return 0;
    }

    public void enterPatientInfo() {
        Scanner sc = new Scanner(System.in);
        String name = null;
        String pas = null;
        long phn = 0;
        int age = 0;
        String gender = null;
        BufferedWriter bw = null;
        do {
        	try {
            System.out.println("Enter patient name:");
            name = sc.next();
            System.out.println("Enter password:");
            pas = sc.next();
            String hashedPassword = hashPassword(pas);
            System.out.println("Enter phone number:");
            phn = sc.nextLong();
            System.out.println("Enter age:");
            age = sc.nextInt();
            System.out.println("Enter gender (m or f):");
            gender = sc.next();
                bw = new BufferedWriter(new FileWriter("patientInformation.txt", true));
                bw.write(name + "," + hashedPassword + "," + phn + "," + age + "," + gender + "\n");
                System.out.println("Patient information added successfully.");
            } catch (IOException e) {
            	System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace(); 
                } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        System.out.println("Error closing BufferedWriter: " + e.getMessage());
                    }
                }
            }

            System.out.println("Do you want to enter another patient? (1 for Yes, 0 for No)");
        } while (sc.nextInt() == 1);
    sc.close();
    }

    public void enterDoctorInfo() {
        Scanner sc = new Scanner(System.in);
        String name = null;
        String pas = null;
        long phn = 0;
        int age = 0;
        String gender = null;
        BufferedWriter bw = null;
        
        	try {
        		
            System.out.println("Enter doctor name:");
            name = sc.next();
            System.out.println("Enter password:");
            pas = sc.next();
            String hashedPassword = hashPassword(pas);
            System.out.println("Enter phone number:");
            phn = sc.nextLong();
            System.out.println("Enter age:");
            age = sc.nextInt();
            System.out.println("Enter gender (m or f):");
            gender = sc.next();
            bw = new BufferedWriter(new FileWriter("doctorInformation.txt", true));
            bw.write(name + "," + hashedPassword + "," + phn + "," + age + "," + gender + "\n");
            System.out.println("Doctor information added successfully.");
        	}catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace(); 
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e) {
                            System.out.println("Error closing BufferedWriter: " + e.getMessage());
                        }
                    }
                }
        
        sc.close();
    }


    public void viewInformation() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. View Patient Information");
        System.out.println("2. View Doctor Information");
        try {
        int choice = sc.nextInt();
        
        switch (choice) {
            case 1:
                viewPatientInformation();
                break;
            case 2:
                viewDoctorInformation();
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace(); }
        sc.close();
    }

    private void  viewPatientInformation() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("patientInformation.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split(",");
                System.out.println("Name: " + str[0]);
                System.out.println("Phone number: " + str[2]);
                System.out.println("Age: " + str[3]);
                System.out.println("Gender: " + str[4]);
                System.out.println("----------------------");
            }
        } catch (IOException e) {
            System.out.println("Error reading patient information: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing BufferedReader: " + e.getMessage());
                }
            }
        }
    }

    private void viewDoctorInformation() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("doctorInformation.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split(",");
                System.out.println("Name: " + str[0]);
                System.out.println("Phone number: " + str[2]);
                System.out.println("Age: " + str[3]);
                System.out.println("Gender: " + str[4]);
                System.out.println("----------------------");
            }
        } catch (IOException e) {
            System.out.println("Error reading doctor information: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing BufferedReader: " + e.getMessage());
                }
            }
        }
    }


    public void viewOwnPatientInformation(String patientName) {
 BufferedReader br = null;
 try {
     br = new BufferedReader(new FileReader("patientInformation.txt"));
     String line;
     boolean found = false;
     while ((line = br.readLine()) != null) {
         String[] str = line.split(",");
         if (str[0].equals(patientName)) {
             System.out.println("Name: " + str[0]);
             System.out.println("Phone number: " + str[2]);
             System.out.println("Age: " + str[3]);
             System.out.println("Gender: " + str[4]);
             System.out.println("----------------------");
             found = true;
             break;
         }
     }
     if (!found) {
         System.out.println("Patient not found or invalid credentials.");
     }
 } catch (IOException e) {
     System.out.println("Error reading patient information: " + e.getMessage());
 } finally {
     if (br != null) {
         try {
             br.close();
         } catch (IOException e) {
             System.out.println("Error closing BufferedReader: " + e.getMessage());
         }
     }
 }
}

    public void viewPatientInformationWrapper() {
        viewPatientInformation();
    }

    
}




