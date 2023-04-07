import com.admin.Employee;
import com.admin.EmployeeDao;
import com.connection.ConnectionBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean databaseFlag = true;
        boolean loginFlag = true;

        while (databaseFlag) {
            System.out.println("enter database username");
            String username = br.readLine();
            System.out.println("enter database password");
            String password = br.readLine();
            ConnectionBuilder.createConnection(username, password);

            if(!databaseFlag){
                System.out.println("invalid password... try again !");
            }

            if(username.equals("root") && password.equals("root")){
                databaseFlag=false;
            }
        }
//++++++++++++++++++++++++database connected++++++++++++++++++++++++++

        while (loginFlag) {
            System.out.println("enter personal username : ");
            String userId = br.readLine().toLowerCase();
            System.out.println("enter personal password : ");
            String userPassword = br.readLine().toLowerCase();

            Pattern admin = Pattern.compile("(admin)[0-9]*");
            Pattern user = Pattern.compile("(user)([0-9]*)");

            Matcher adminMatcher = admin.matcher(userId);
            Matcher userMatcher = user.matcher(userId);

            boolean resultAdmin = adminMatcher.matches();
            boolean resultUser = userMatcher.matches();

            if (resultAdmin) {
                loginFlag = false;
                System.out.println("logged in to admin account");
                System.out.println("welcome to admin panel, what would you like to do?");
                while (true) {
                    System.out.println("1.add new employee\n2.update existing employee detail\n3.delete employee" +
                            "\n4.get every employee detail\n5. get detail by user id\n6. get detail by user name\n7.exit app");
                    int adminChoice = 0;
                    try {
                        adminChoice = Integer.parseInt(br.readLine());
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("choose option from numbers");
                    }
                    switch (adminChoice) {
                        case 1 -> {
                            //add

                            Pattern validateEmail = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z.-]+\\.com");
                            Pattern validatePhone = Pattern.compile("[7-9]\\d{9}");

                            System.out.print("name :");
                            String name = br.readLine();
                            System.out.print("id :");
                            int id = Integer.parseInt(br.readLine());
                            System.out.print("address :");
                            String  address = br.readLine();
                            System.out.print("salary :");
                            long salary = Long.parseLong(br.readLine());
                            System.out.print("email :");
                            String email = br.readLine();
                            System.out.println("phone : ");
                            long phone = Long.parseLong(br.readLine());

                            String phoneForVerify = Long.toString(phone);
                            Matcher emailMatch = validateEmail.matcher(email);
                            Matcher phoneMatch = validatePhone.matcher(phoneForVerify);

                            if(emailMatch.matches()) {
                                if(phoneMatch.matches()) {

                                    Employee emp = new Employee(name, id, address, salary, email, phone);
                                    boolean ans = EmployeeDao.addNewEmployee(emp);

                                    if (ans) {
                                        System.out.println("employee added successfully");
                                    } else {
                                        System.out.println("oops... try again !!!");
                                    }
                                    System.out.println(emp);
                                }else {
                                    System.out.println("detail not submitted invalid phone number");
                                }
                            }else {
                                System.out.println("detail not submitted invalid e-mail");
                            }
                        }
                        case 2->{
                            //UPDATE
                            System.out.println("select choice you want to update");
                            System.out.println("1.name\n2.id\n.3.address\n4.salary\n5.email\6.phone");
                            int updateChoice = Integer.parseInt(br.readLine());

                            if(updateChoice==2){
                                System.out.println("enter name of employee : ");
                                String name = br.readLine();

                                boolean ans = EmployeeDao.updateEmployeeDetail(name, updateChoice);
                                if (ans) {
                                    System.out.println("detail updated successfully");
                                } else {
                                    System.out.println("oops.... try again !!!");
                                }

                            }else {
                                System.out.println("enter following id to update detail");
                                int id = Integer.parseInt(br.readLine());

                                boolean ans = EmployeeDao.updateEmployeeDetail(id, updateChoice);
                                if (ans) {
                                    System.out.println("detail updated successfully");
                                } else {
                                    System.out.println("oops.... try again !!!");
                                }
                            }

                        }
                        case 3->{
                            System.out.println("enter id you want to delete employee");
                            int id = Integer.parseInt(br.readLine());
                            boolean ans = EmployeeDao.deleteEmployee(id);
                            if (ans) {
                                System.out.println("employee deleted successfully");
                            } else {
                                System.out.println("oops.... try again !!!");
                            }
                        }
                        case 4->{
                            //get every user detail
                            EmployeeDao.showAllEmployees();
                        }
                        case 5->{
                            System.out.println("enter id to get user detail");
                            int id = Integer.parseInt(br.readLine());
                            EmployeeDao.detailByIdForAdmin(id);
                        }
                        case 6->{
                            System.out.print("enter name of user : ");
                            String name = br.readLine();
                            EmployeeDao.detailByNameForAdmin(name);


                        }
                        case 7-> System.exit(0);
                    }
                }
            }

            if (resultUser) {
                loginFlag = false;
                System.out.println("logged in to user account");

                while (true) {
                    System.out.println("what would you like to do?");
                    System.out.println("1. see all employee details");
                    System.out.println("2. get employee detail by id");
                    System.out.println("3. get employee detail by name");
                    System.out.println("4. edit your personal details");
                    System.out.println("5. exit app");
                    int userChoice = 0;

                    try {
                        userChoice = Integer.parseInt(br.readLine());

                        switch (userChoice) {
                            case 1 -> {
                                //all employee detail
                                EmployeeDao.allEmployeesToUser();
                            }
                            case 2 -> {
                                //detail by id
                                System.out.println("enter id of employee you want to see detail");
                                int id = Integer.parseInt(br.readLine());
                                EmployeeDao.detailByIdForUser(id);
                            }
                            case 3 -> {
                                //detail by name
                                System.out.println("enter name of employee");
                                String name = br.readLine();
                                EmployeeDao.detailByNameForUser(name);
                            }
                            case 4 -> {
                                //edit your personal details
                                String id = userMatcher.group(2);
                                int changeInfoId = Integer.parseInt(id);
                                System.out.println("your id : "+changeInfoId);

                                System.out.println("what would you like to update/edit");
                                System.out.println("1. address\n2. email\n3. phone");
                                int userUpdateChoice = Integer.parseInt(br.readLine());

                                boolean ans =  EmployeeDao.editYourPersonalDetailsForUser(changeInfoId,userUpdateChoice);
                                if(ans){
                                    System.out.println("details updated successfully");
                                }else {
                                    System.out.println("error, details not updated");
                                }

                            }
                            case 5->{
                                //exit
                                System.exit(0);
                            }
                            default -> {
                                System.out.println("select valid option");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}