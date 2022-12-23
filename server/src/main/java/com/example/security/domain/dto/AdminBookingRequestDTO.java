package com.example.security.domain.dto;



public class AdminBookingRequestDTO {



        private int year;

        private int month;

        private int day;


        private int deskId;

        private int userId;


        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getDeskId() {
            return deskId;
        }

        public void setDeskId(int deskId) {
            this.deskId = deskId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }


