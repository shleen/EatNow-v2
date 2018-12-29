package com.example.eatnow.eatnow;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

public class PaymentActivity extends BaseActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference pending_orders = database.getReference("orders/pending");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_payment);
        super.onCreate(savedInstanceState);

        CardForm cardForm = (CardForm)findViewById(R.id.cardform);

        // Calculate & display total
        displayTotal();

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(PaymentActivity.this,"Name : " + card.getName() + " | Last 4 Digits : "+card.getLast4(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayTotal()
    {
        final TextView txtDes = (TextView)findViewById(R.id.payment_amount);
        final Button btnPay = (Button)findViewById(R.id.btn_pay);

        DatabaseReference user_pending_order = pending_orders.child(Help.stripPath(auth.getCurrentUser().getEmail()));
        user_pending_order.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        double total = Help.calculateTotal(ds);

                        NumberFormat formatter = NumberFormat.getCurrencyInstance();
                        txtDes.setText(formatter.format(total));

                        btnPay.setText(String.format("Pay %s", formatter.format(total)));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }
}
