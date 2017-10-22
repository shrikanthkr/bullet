# Beta 0.1 - Broadcast Helper

Annotated Methods and Notification helpers for android. This library helps keep you notification receiver code clean and simple.

# Usage:

--- 

> Register your notification api in the base class


 - Can be used with Activity/Fragment
```
public class BaseActivity extends AppCompatActivity {

    protected String id = UUID.randomUUID().toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            id = savedInstanceState.getString("UNIQUE_ID");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        NotificationApi.getInstance().register(this, id);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationApi.getInstance().unRegister(id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationApi.getInstance().destroy(id);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("UNIQUE_ID", id);
    }
}

```


> Usages as method in child classes. 

```
public class MainActivity extends BaseActivity {
    
     ... Other activity code ... 
     
    @Subscribe(id ="login", threadMode = ThreadMode.MAIN)
    public void onLogin(String message){
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Subscribe(id ="logout")
    public void logout(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

}

```

> The registration and annotated methods are not restricted to acitivties or fragments. Any java class can register with a UUID and annotate the methods. But ensure you clean up the registration.

```
public class POJOJava{
    String id = UUID.randomUUID().toString();
    
    public POJOJava(){
       NotificationApi.getInstance().register(this, id);
    }
    
    @Subscribe(id ="login")
    public void onLogin(String message){
            //do something here
    }
    
    pubic void cleanUp(){
      NotificationApi.getInstance().destroy(id);
    }
}

```

> Thread Modes:

  - POST - runs on the context of caller
  - MAIN - runs on main thread
  - ASYNC - runs asynchronously on a separate thread
  
  
## TODO's

- Cleanup threads and make the architecture more robust
- Add android architecture components for easy usage
- Make subscriptions sticky by default, if the receiver is on main thread



## License - MIT