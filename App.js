/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View , DeviceEventEmitter } from 'react-native';
import ToastExample from "./ToastExample";
import ImagePickerExample from "./ImagePickerExample";



const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {
  componentDidMount() {
    DeviceEventEmitter.addListener("onRefreshMessage",this.onUpdateMessage)
  }

  componentWillUnmount() {
    DeviceEventEmitter.removeListener("onRefreshMessage",this.onUpdateMessage)
  }

  onUpdateMessage = (e) => {
    console.log(e.string)
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>To get started, edit App.js11</Text>
        <Text style={styles.instructions}>{instructions}</Text>
        <Text style={styles.welcome} onPress={
          ()=>ToastExample.show(
            "js中点的啊",
            ToastExample.SHORT,
            (message,count)=>{console.log("==",message,count)},
            (message,count)=>{console.log("++",message,count)})
        }>
         调用原生Toast!
        </Text>
        <Text style={styles.welcome} onPress={
          async () => {
            var {relativeX,relativeY,width,height } = await ToastExample.measureLayout(1,1);
            console.log(relativeX,relativeY,width,height);
          }
        }

        >
        调用原生Toast promise!
        </Text>
        <Text style={styles.welcome}  onPress= {
            async () => {
              var res = await ImagePickerExample.pickImage()
              console.log(res)
            }
        }>
        调用原生的Picker image
        </Text>
        
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
