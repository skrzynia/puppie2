package org.wit.puppie2.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import timber.log.Timber.Forest.i

class NewPostActivit: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        i("Activity started")

        setContent{
            createScaffold()
        }
    }
    @Preview
    @Composable
    fun createScaffold(){

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Puppie2",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "ArrowBack")
                        }
                    })
            }
        ) {
                paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                OutlinedTextField(value = "Name", onValueChange = {/*TODO*/}, modifier= Modifier.fillMaxWidth() )
                OutlinedTextField(value = "Address", onValueChange = {/*TODO*/}, modifier= Modifier.fillMaxWidth() )
            }
        }
    }
}