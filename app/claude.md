# Project Overview

This project is an Android quiz app designed for practicing German. The app shows an incomplete sentence with a missing word, and the user must choose the correct answer from four multiple-choice options. Each question has only one correct answer.

## Core Features

### Main Menu - Germany Map

The first screen displays a map of Germany with clickable city markers. Each city represents a level and can be clicked to start that level's quiz.

**Clickable Cities:**
- **Hamburg** - Level 1
- **Berlin** - Level 2
- **Leipzig** - Level 3
- **Düsseldorf** - Level 4
- **Cologne** - Level 5
- **Frankfurt** - Level 6 
- **Stuttgart** - Level 7 
- **Munich** - Level 8

**Map Interaction:**
- Each city is represented by a clickable marker/icon on the map
- Completed cities show different visual indicators (e.g., checkmarks, different colors)
- Locked cities (not yet unlocked) are grayed out or show lock icons
- Clicking a city navigates to that level's quiz screen

### Question Display

At the top of the screen, show an incomplete German sentence with a blank (....) where the missing word should be.

**Example:**
```
ich hätte gern ein .... Kaffee
```


### Answer Options

Four buttons (or cards) are shown below the sentence.

**Example options:**
- kleines ✅ (correct)
- zwei
- schon
- bezahlen

### Answer Validation

**If the user selects the correct option:**
- Fill the blank in the sentence
- Show a green checkmark (✔) or a positive animation

**If the user selects the wrong option:**
- Show red feedback (e.g., shake animation or ❌)
- Lose one heart
- reveal the correct answer and go to the next question or game over if no hearts left

### Hearts System

Each level starts with 3 hearts (❤️❤️❤️).

- Wrong answer = Lose one heart
- When all hearts are lost, game over
- Show game over popup with options:
  - Restart level
  - Return to main menu
- hearts will be reset on each level(city)

### Level System

Each level is associated with a German city:

- **Hamburg** - Level 1
- **Berlin** - Level 2
- **Leipzig** - Level 3
- **Düsseldorf** - Level 4
- **Cologne** - Level 5
- **Frankfurt** - Level 6
- **Stuttgart** - Level 7
- **Munich** - Level 8
you can find the questions/answers of each city in assets/questions/city_name.json


### Scoring

- Each correct answer = +10 points
- Wrong answers = 0 points (optional: deduct points if needed)
- Show total score at the end of the quiz
- the score displayed on top of screen in left

### Progression

- After answering, move automatically to the next question
- Quiz ends when the last question is completed
- Complete a level to unlock the next city

## Question Flow Logic
The questions count depends on the json file of each city
they should come in random order
after the level successfully completed, the points will be shown with an animation with a continue button. when clicked, player goes to the City Progression activity 


## Question Bank (JSON)

Questions, options, and answers are stored in a JSON file.

**Example format:**
```json
{
  "level": 2,
  "city": "Berlin",
  "background": "images/backgrounds/berlin.png",
  "questions": [
    {
      "sentence": "ich .... in Berlin",
      "options": ["wohne", "wohnt", "wohnst", "wohnen"],
      "answer": "wohne",
      "hint": "با ضمیر ich (من) فعل پایانه -e می‌گیرد"
    }
  ]
}
```

**Background Loading:**
- Each quiz activity must load the background image from the JSON file's "background" field
- The background path (e.g., "images/backgrounds/berlin.png") should be loaded dynamically for each level
- Apply the background image to the quiz activity layout when the level starts

## Extra Features

### Audio Support
- Use Android TTS (Text-to-Speech) to read the question aloud


### City Progression
- Display city landmarks or backgrounds
- Unlock achievements for completing cities
- Show progress map of Germany

## Tech Stack

- **Platform:** Android Studio
- **Language:** Java or Kotlin
- **UI Components:**
  - ImageView → Germany map background
  - ImageButton/Clickable areas → city markers on map
  - TextView → sentence display
  - Button (or MaterialCardView) → answer options
  - ImageView → heart icons
  - Dialog → game over popup
- **Data Source:** Local JSON file for each city
- **Assets:** Germany map image (JPG format) and city marker icons