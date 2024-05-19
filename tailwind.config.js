/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,java}"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
  daisyui: {
    darkTheme: false,
  },
}

