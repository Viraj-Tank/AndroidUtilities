package com.novuspax.androidutilities.utils.annotations

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.annotation.Inherited
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.ExperimentalReflectionOnLambdas
import kotlin.reflect.jvm.reflect


//@Retention annotation specifies the lifespan of the annotation
//@Target annotation specifies the elements that the annotation can be applied to


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@Inherited
annotation class PermissionCheck(val permission: String = Manifest.permission.CAMERA)

fun withPermissionCheck(context: Context, activity: Activity, function: () -> Unit) {

    val functionRef = function.reflect()
    val permissionAnnotation = functionRef?.annotations?.firstOrNull { it is PermissionCheck } as PermissionCheck?
    if (permissionAnnotation != null) {
        if (checkPermission(context, permissionAnnotation.permission)) {
            function()
        } else {
            requestPermission(activity, permissionAnnotation.permission)
        }
    }


    /*val functionRef = function::class.java.declaredFunctions.first()
    val permissionAnnotation = functionRef.findAnnotation<PermissionCheck>()
    if (permissionAnnotation != null) {
        if (checkPermission(context, permissionAnnotation.permission)) {
            function()
        } else {
            requestPermission(activity, permissionAnnotation.permission)
        }
    }*/


    /*val permission = function.annotations.first { it is PermissionCheck } as PermissionCheck
    if (checkPermission(context, permission.permission)) {
        function()
    } else {
        requestPermission(activity, permission.permission)
    }*/
}


fun checkPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}

fun requestPermission(activity: Activity, permission: String) {
    ActivityCompat.requestPermissions(activity, arrayOf(permission), 0)
}