# GitHub User Explorer (Android · Kotlin)

A native Android app for browsing GitHub users — search users, view profile details, and explore followers/following — built as a Dicoding Android learning submission.

## Features

- Search GitHub users via GitHub REST API
- User detail with profile info
- Followers / Following tabs (ViewPager2 + TabLayout)
- Image loading with Glide + CircleImageView

## Architecture & Stack

- **MVVM**: `view` → `modelview` (ViewModel + LiveData) → `serviceapi` (Retrofit)
- **Retrofit 2 + OkHttp** (with logging interceptor) for networking
- **RecyclerView** adapters for user lists
- Kotlin, AndroidX, Material Components — minSdk 21

```
app/src/main/java/.../
├── model/        # Data classes
├── modelview/    # ViewModels
├── serviceapi/   # Retrofit service
├── adapter/      # RecyclerView adapters
└── view/         # Activities/Fragments
```

> 📚 This is an early learning project (Dicoding submission). For my current Android/Kotlin work, see [kmp_todo_app](https://github.com/FaaStatic/kmp_todo_app) — Compose Multiplatform with a Ktor backend.
