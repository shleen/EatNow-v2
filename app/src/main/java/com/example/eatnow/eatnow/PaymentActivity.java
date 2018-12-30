package com.example.eatnow.eatnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    DatabaseReference processing_orders = database.getReference("orders/processing");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_payment);
        super.onCreate(savedInstanceState);

        CardForm cardForm = (CardForm)findViewById(R.id.cardform);

        // Calculate & display total
        displayTotal();

        // TODO: Remove pre-filled fields
        TextView textName = (TextView) findViewById(R.id.card_name);
        textName.setText("sheline");
        TextView textCardNo = (TextView) findViewById(R.id.card_number);
        textCardNo.setText("4242424242424242");
        TextView textCardExp = (TextView) findViewById(R.id.expiry_date);
        textCardExp.setText("02/20");
        TextView textCardCVC = (TextView) findViewById(R.id.cvc);
        textCardCVC.setText("222");

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                // Payment complete. Move order to processing
                final DatabaseReference user_processing_orders = processing_orders.child(Help.stripPath(auth.getCurrentUser().getEmail()));
                user_processing_orders.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Help.moveToProcessing(pending_orders,
                                          user_processing_orders.child(Integer.toString(Help.getNextId(dataSnapshot))),
                                          Help.stripPath(auth.getCurrentUser().getEmail()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }
                );

                // TODO: Ping kitchen staff

                // Display success message
                Toast.makeText(PaymentActivity.this,"Order placed! We'll notify you once it's ready for collection!", Toast.LENGTH_LONG).show();

                // Redirect back to main page
                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i);
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
