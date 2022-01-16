# Polaroid
A simple image loading library to learn how it works.

This is inspired from Glide, so you will find almost similar structure, but in no way it can replace that xD

To load an image,
1. it can be as simple as this
```
imageView.loadImage(imageUrl)
```
2. or can be as customizable as this
```
imageView2.loadImage(imageUrl) {
            scoped { lifecycleScope }
            placeholder { R.color.black }
            transformInto { PolaroidTransformations.CircularTransformation }
            onSuccessLoad {
                Log.d("PolaroidLoad", "success")
            }
        }
```        

