package model;

import profileapp.TopTen;
import profileapp.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TopTenImpl extends TopTen{
	
	public TopTenImpl(){
		this.topTenUsers = new User[10];
	}

	public void writeInOutputFile(String nameFile) throws IOException {
        File file = new File(nameFile);
        // clean output file
        if (file.exists())
            if (!file.delete())
                System.err.println("Error : Imposible to clean the output file.");
        for (User user : this.topTenUsers) {
            System.out.println(user);
            FileWriter fw = new FileWriter(nameFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(((UserImpl)user).toOutputFormat()+ "\n");
            bw.flush();
        }
	}

}
