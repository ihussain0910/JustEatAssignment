
# 🍽️ JustEat Restaurant Finder App

This Android application displays a list of restaurants based on the Just Eat API. It showcases modern Android development using **CardViews**, **Chips**, and clean UI practices.

- Shows the **first 10 restaurants** from the API.
- Displays:
  - ✅ Restaurant name
  - 🍜 Cuisines (as Chips)
  - 🎁 Deals (as Chips)
  - ⭐ Average rating
  - 📍 Restaurant address

---

## 📸 UI Preview

Each restaurant is displayed in a clean card with elevated shadows and rounded corners. Cuisines and deals are shown using `Chip`s to give a modern look and feel. Please note I split the cuisines into Cuisines and deals as some cuisines had deals.

---

## 🚀 How to Build, Compile, and Run

### ✅ Prerequisites

- **Android Studio Hedgehog or later**
- **Gradle 8+**
- **Android SDK 33+**
- **Kotlin 1.8+**

---

### 🛠️ Build and Run Steps

1. **Clone the repository:**

```bash
git clone https://github.com/ihussain0910/JustEatAssignment.git
cd JustEatAssignment
```

2. **Open in Android Studio:**

- File → Open → Select the `JustEatAssignment` directory

3. **Let Gradle sync** (Android Studio will handle this automatically)

4. **Run the app:**

- Click ▶️ on the toolbar  
- Or use: `Run > Run 'app'`  
- Choose a device or emulator with **API 33+**

---

## 📂 Project Structure

```
📁 app
 ┣ 📂 java/com.justeat
 ┃ ┣ 📄 MainActivity.kt
 ┃ ┣ 📄 Restaurant.kt
 ┃ ┗ 📄 RestaurantAdapter.kt
 ┣ 📂 res
 ┃ ┣ 📁 layout
 ┃ ┃ ┗ 📄 item_restaurant.xml
 ┃ ┗ 📁 values
 ┃    ┗ 📄 colors.xml, styles.xml
 ┗ 📄 AndroidManifest.xml
```

---
s
## 📡 API & Data

- Consumes data from Just Eat's **open REST API**.
- Parses JSON and maps cuisine types and deals using ChipViews.
- Filters cuisines and deals from a shared tag list in the JSON.

---

## 🚀 App Improvements

### ✅ Filter Functionality
- Add interactive filters for cuisines and deals at the top of the list.
- Use `Chip`s to represent filters that dynamically update the restaurant list in real-time.

### ⏳ Loading & Error States
- Show a loading spinner or shimmer effect while data is being fetched.
- Display meaningful error messages when the API call fails.

### 🌗 Dark Mode Support
- Implement support for both Light and Dark themes to enhance user experience across devices.


## 🔗 GitHub Repository

🔗 [https://github.com/ihussain0910/JustEatAssignment](https://github.com/ihussain0910/JustEatAssignment)
