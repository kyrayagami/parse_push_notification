import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:parse_push_notification/parse_push_notification.dart';

void main() {
  const MethodChannel channel = MethodChannel('parse_push_notification');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await ParsePushNotification.platformVersion, '42');
  });
}
