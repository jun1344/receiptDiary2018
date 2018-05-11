package com.example.junsic.receiptdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    //Server Test User ID
    static String userID = "userID";
    static String password = "password";
    static String serverIP = "210.89.190.224";

    EditText editTextId;
    EditText editTextPassword;
    Button signupButton;
    Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);

        signinButton = findViewById(R.id.signInButton);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread() {
                    public void run() {
                        OutputStream out = null;
                        FileInputStream fin;

                        try {
                            Socket soc = new Socket(serverIP, 11111);
                            //System.out.println("Server Connected!");            // 11111 is Server port number
                            out = soc.getOutputStream();                         // Create outputstream to socket
                            DataOutputStream dout = new DataOutputStream(out);
                            InputStream in = soc.getInputStream();             // Create inputstream to socket
                            DataInputStream din = new DataInputStream(in);

                            dout.writeUTF("Signup");

                            JSONObject user = new JSONObject();   // Use JSON to store information which has to be stored at server
                            user.put("id", "junsic");
                            user.put("password", "1344");
                            dout.writeUTF(user.toString());

                        } catch (Exception e) {
                        }
                    }
                };
                t.start();
            }
        });

        signupButton = findViewById(R.id.button1);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread() {
                    public void run() {
                        OutputStream out = null;
                        FileInputStream fin;

                        try {
                            Socket soc = new Socket(serverIP, 11111);
                            //System.out.println("Server Connected!");            // 11111 is Server port number
                            out = soc.getOutputStream();                         // Create outputstream to socket
                            DataOutputStream dout = new DataOutputStream(out);
                            InputStream in = soc.getInputStream();             // Create inputstream to socket
                            DataInputStream din = new DataInputStream(in);

                            dout.writeUTF("Login");

                            JSONObject user = new JSONObject();   // Use JSON to store needed information
                            user.put("id", editTextId.getText().toString());
                            user.put("password", editTextId.getText().toString());
                            dout.writeUTF(user.toString());      // Transmit JSON information to server
                            String result = din.readUTF();      // Receive result from user
                            if (result.equalsIgnoreCase("Success"))
                            {
                                userID = editTextId.getText().toString();
                                password = editTextId.getText().toString();
                            }else                        // If failed
                            {
                            }

                        } catch (Exception e) {
                        }
                    }
                };
                t.start();
            }
        });

    }
}



