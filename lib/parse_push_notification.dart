import 'dart:async';

import 'package:flutter/services.dart';

class ParsePushNotification {
  static const MethodChannel _channel =
      const MethodChannel('parse_push_notification');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static get showAlertDialog async{
    await _channel.invokeMethod('showAlertDialog');
  }

  // "http://192.168.2.1:1337/parse/"

  static Future<dynamic> autoInitParse(serverUrl, applicationId) async{
    await _channel.invokeMethod('autoInitParse',{"serverUrl" :serverUrl, "applicationId": applicationId} );
  }
  /*
  static Future<bool> get autoInitParse async{
    return await _channel.invokeMethod<bool>('autoInitParse');
  } 
  */
}
