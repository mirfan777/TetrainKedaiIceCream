/** @type {import('tailwindcss').Config} */
module.exports = {
  mode: 'jit',
  content: ["./src/**/*.{html,js,java}"],
  theme: {
    extend: {
      animation: {
        fadeOut: 'fadeOut 0.5s ease-out',
      },
      keyframes: {
        fadeOut: {
          '0%': { opacity: 1 },
          '100%': { opacity: 0 },
        },
      },
    },
  },
  plugins: [require("daisyui")],
  daisyui: {
    darkTheme: false,
  },
}