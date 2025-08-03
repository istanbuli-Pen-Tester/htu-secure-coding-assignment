package finaSC;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class PatientAndDoctorInfoTest {



    @Test
    public void testLoginAsDoctor() {
        PatientAndDoctorInfo app = new PatientAndDoctorInfo();
        int actual = app.login("saif", "1324");
        assertEquals(2, actual);
    }

    @Test
    public void testLoginAsPatient() {
        PatientAndDoctorInfo app = new PatientAndDoctorInfo();
        int actual = app.login("abdullah", "1324"); 
        assertEquals(3, actual);
    }

    
}