package com.andoresu.gordiskyapp.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.authorization.data.User;
import com.andoresu.gordiskyapp.authorization.login.LoginActivity;
import com.andoresu.gordiskyapp.client.ErrorResponse;
import com.andoresu.gordiskyapp.core.loandetail.LoanDetailFragment;
import com.andoresu.gordiskyapp.core.loans.LoansContract;
import com.andoresu.gordiskyapp.core.loans.LoansFragment;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.newloan.NewLoanContract;
import com.andoresu.gordiskyapp.core.newloan.NewLoanFragment;
import com.andoresu.gordiskyapp.core.people.PeopleContract;
import com.andoresu.gordiskyapp.core.people.PeopleFragment;
import com.andoresu.gordiskyapp.core.person.PersonContract;
import com.andoresu.gordiskyapp.core.person.PersonFragment;
import com.andoresu.gordiskyapp.core.resume.ResumeFragment;
import com.andoresu.gordiskyapp.core.transaction.TransactionContract;
import com.andoresu.gordiskyapp.core.transaction.TransactionFragment;
import com.andoresu.gordiskyapp.core.transactions.TransactionsContract;
import com.andoresu.gordiskyapp.core.transactions.TransactionsFragment;
import com.andoresu.gordiskyapp.security.SecureData;
import com.andoresu.gordiskyapp.utils.BaseActivity;
import com.andoresu.gordiskyapp.utils.BaseFragment;

import java.util.List;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andoresu.gordiskyapp.utils.BaseFragment.BASE_TAG;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainContract.View,
        PeopleContract.InteractionListener,
        LoansContract.InteractionListener,
        NewLoanContract.InteractionListener, PersonContract.InteractionListener, TransactionsContract.InteractionListener, TransactionContract.InteractionListener {

    public static final String TAG = BASE_TAG + MainActivity.class.getSimpleName();

    private MainContract.ActionsListener actionsListener;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private HeaderViewHolder headerViewHolder;

    private User user;

    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setTitle(R.string.app_name);

        user = (User) getIntent().getSerializableExtra(User.NAME);

        headerViewHolder = new HeaderViewHolder(navigationView.getHeaderView(0));
        setUserNameToMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        actionsListener = new MainPresenter(this, this, SecureData.getToken());

        setLoansFragment();
    }

    private void setUserNameToMenu(){
        headerViewHolder.navHeaderNameTextView.setText(user.getName());
        headerViewHolder.navHeaderEmailTextView.setText(user.email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for(Fragment f : fragments){
                if(f instanceof LoanDetailFragment){
                    Log.i(TAG, "onBackPressed: asd");
                    getSupportFragmentManager().popBackStack();
                    setLoansFragment();
                    return;
                }
            }
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.navCustomer:
                setPeopleFragment();
                break;
            case R.id.navLoans:
                setLoansFragment();
                break;
            case R.id.navResume:
                setResumeFragment();
                break;
            case R.id.navTransactions:
                setTransactionsFragment();
                break;
            case R.id.navProfile:
                Toast.makeText(this, "Funcion En progreso", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navLogout:
                actionsListener.logout();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setPeopleFragment(){
        PeopleFragment fragment = PeopleFragment.newInstance(this);
        changeFragment(fragment);
    }

    private void setResumeFragment(){
        ResumeFragment fragment = ResumeFragment.newInstance();
        changeFragment(fragment);
    }

    private void setTransactionsFragment(){
        TransactionsFragment fragment = TransactionsFragment.newInstance(this);
        changeFragment(fragment);
    }


    @Override
    public void goToPersonDetail(Person person) {
        PersonFragment fragment = PersonFragment.newInstance(person, this);
        changeFragment(fragment);
    }

    private void setLoansFragment(){
        LoansFragment fragment = LoansFragment.newInstance(this);
        changeFragment(fragment);
    }

    @Override
    public void goToLoanDetail(Loan loan) {
        BaseFragment fragment;
        if(loan == null || loan.id == null){
            fragment = NewLoanFragment.newInstance(this);
        }else{
            fragment = LoanDetailFragment.newInstance(loan);
        }
        changeFragment(fragment);
    }

    private void changeFragment(BaseFragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setTitle(fragment.getTitle());
        currentFragment = fragment;
    }

    @Override
    public void showProgressIndicator(boolean active) {
        progressBar.setVisibility( active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showGlobalError(ErrorResponse errorResponse) {

    }

    @Override
    public void onLogoutFinish() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void goToPersonLoans(Person person) {
        LoansFragment fragment = LoansFragment.newInstance(this, person.id);
        changeFragment(fragment);
    }

    @Override
    public void goToNewTransaction() {
        setTransactionFragment();
    }

    private void setTransactionFragment() {
        TransactionFragment fragment = TransactionFragment.newInstance(this);
        changeFragment(fragment);
    }

    @Override
    public void goToTransactions() {
        //TODO: check if remove las fragment from stack
        setTransactionsFragment();
    }


    protected static class HeaderViewHolder {

        @BindView(R.id.navHeaderNameTextView)
        TextView navHeaderNameTextView;

        @BindView(R.id.navHeaderEmailTextView)
        TextView navHeaderEmailTextView;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
