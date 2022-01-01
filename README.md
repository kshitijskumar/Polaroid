# Polaroid
A simple image loading library to learn how it works.

This is inspired from Glide, so you will find almost similar structure, but in no way it can replace that xD

Currently how it looks: 

```
PolaroidCamera()
            .scoped(lifecycleScope)
            .load(imageUrl)
            .into(imageView)
            .with(R.color.black)
            .onSuccessLoad {
                Log.d("ImageLoad", "loaded: $it")
            }
            .onFailedLoad {
                Log.d("ImageLoad", "error: $it")
            }
            .display()
```
