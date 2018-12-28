package com.example.eatnow.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.google.firebase.auth.FirebaseAuth;


public class Payment extends BaseActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_payment);
        super.onCreate(savedInstanceState);

        CardForm cardForm = (CardForm)findViewById(R.id.cardform);
        TextView txtDes = (TextView)findViewById(R.id.payment_amount);
        Button btnPay = (Button)findViewById(R.id.btn_pay);

        txtDes.setText("$1999");
        btnPay.setText(String.format("Payer %s",txtDes.getText()));

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(Payment.this,"Name : " + card.getName() + " | Last 4 Digits : "+card.getLast4(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
