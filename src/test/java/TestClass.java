import com.mahmoud.rest.endpoints.Users;
import com.mahmoud.rest.models.UserPojo;
import com.mahmoud.tools.CSVReader;
import com.mahmoud.ui.BrowserDriver;
import com.mahmoud.ui.pages.htmltables.HTMLTablesPage;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class TestClass {
    @Test
    public void Test_XPath_function() {
        HTMLTablesPage.getActions().navigate();
        WebElement customersTable = HTMLTablesPage.getVerify().getCustomerTable();
        try {
            String country = HTMLTablesPage.getVerify().getTableCellTextByXpath(
                    customersTable,
                    1,
                    "Alfreds Futterkiste",
                    3);
            assert country.equals("Germany"): String.format("The actual value is '%s' but the expected is 'Germany'",country);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void Test_Verify_function() {
        HTMLTablesPage.getActions().navigate();
        WebElement customersTable = HTMLTablesPage.getVerify().getCustomerTable();
        try {
            List<Map<String,String>> dataToCheck = CSVReader.getDataFromCSV("/data.csv");
            for (Map<String,String> map:dataToCheck
                 ) {
                int searchColumn = Integer.parseInt(map.get("searchColumn"));
                String searchText = map.get("searchText");
                int returnColumn = Integer.parseInt(map.get("returnColumn"));
                String expectedText = map.get("expectedText");

                boolean isVerified = HTMLTablesPage.getVerify().verifyTableCellText(
                        customersTable,
                        searchColumn,
                        searchText,
                        returnColumn,
                        expectedText);

                assert isVerified: String.format("Wrong data at '%d','%s','%d','%s'",
                        searchColumn, searchText, returnColumn, expectedText);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void Close_Browser() {
        assert BrowserDriver.closeBrowser(): "Error while closing the browser";
    }

    @Test
    public void Test_Rest_Gender_Equals() {
        // Get current users list
        List<UserPojo> users = Users.getUsersList();

        assert users != null: "can't get the users list";

        int maleCount = 0;
        int femaleCount = 0;
        // count
        for (UserPojo user:users
             ) {
            if(user.getGender().equalsIgnoreCase("male")) {
                maleCount++;
            }
            else if(user.getGender().equalsIgnoreCase("female")) {
                femaleCount++;
            }
        }

        Random random = new Random();
        // if not equal add users till equal
        while (maleCount!=femaleCount) {
            String name;
            String status = "active";
            String gender;
            if(maleCount > femaleCount) {
                gender = "female";
                name = "female"+random.nextInt(100000000);
                femaleCount++;
            }
            else {
                gender = "male";
                name = "male"+random.nextInt(100000000);
                maleCount++;
            }

            String email = name + "@wow.co.il";

            boolean userAdded = Users.addUser(name,gender,email,status);
            // after add user it will remove the last user from the list
            UserPojo removedUser = users.remove(users.size()-1);
            if(removedUser.getGender().equalsIgnoreCase("male")) {
                maleCount--;
            }
            else if(removedUser.getGender().equalsIgnoreCase("female")) {
                femaleCount--;
            }

            if(!userAdded) {
                System.out.println("user not added");
                break;
            }
        }

        // get users list after add users
        users = Users.getUsersList();

        assert users != null: "can't get the users list";

        maleCount = 0;
        femaleCount = 0;

        for (UserPojo user:users
        ) {
            if(user.getGender().equalsIgnoreCase("male")) {
                maleCount++;
            }
            else if(user.getGender().equalsIgnoreCase("female")) {
                femaleCount++;
            }
        }

        assert maleCount == femaleCount: "male count not equal to female count";
    }

    @Test
    public void removeInactiveUsers() {
        List<UserPojo> inactiveUsers;
        delete_loop:
        do {
            // Get current users list
            List<UserPojo> users = Users.getUsersList();

            assert users != null: "can't get the users list";

            inactiveUsers = users.stream().filter(user->user.getStatus().equalsIgnoreCase("inactive"))
                    .collect(Collectors.toList());

            for (UserPojo user:inactiveUsers
            ) {
                boolean userRemoved = Users.removeUser(user.getId());
                if(userRemoved) {
                    System.out.printf("user %d was removed", user.getId());
                }
                else {
                    break delete_loop;
                }
            }
        }
        while (inactiveUsers.size()!=0);

        assert inactiveUsers.size()!=0: "failed to delete all the inactive users";
    }

    @Test
    public void change_all_email() {
        // Get current users list
        List<UserPojo> users = Users.getUsersList();

        assert users != null: "can't get the users list";

        List<UserPojo> usersToChange = users.stream().filter(user->!user.getEmail().endsWith(".co.il")).collect(Collectors.toList());

        for (UserPojo user:usersToChange
             ) {
            String newEmail = user.getEmail().replaceAll("(@[^.]+).*", "$1.co.il");
            boolean userChanged = Users.changeUserEmail(user,newEmail);
            if(!userChanged) {
                System.out.printf("Unable to change email '%s'",user.getEmail());
                break;
            }
        }

        users = Users.getUsersList();

        assert users != null: "can't get the users list";

        usersToChange = users.stream().filter(user->!user.getEmail().endsWith(".co.il")).collect(Collectors.toList());

        assert usersToChange.size()==0: "didn't change all users emails";
    }
}
