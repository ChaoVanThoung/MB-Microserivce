"use client";

import React, { useState, useEffect } from "react";
import {
  User,
  Mail,
  Phone,
  Calendar,
  Edit2,
  Camera,
  MapPin,
  Briefcase,
} from "lucide-react";

interface ProfileData {
  username: string;
  email: string;
  familyName: string;
  givenName: string;
  phoneNumber: string;
  gender: string;
  dob?: string;
  profileImage?: string;
  coverImage?: string;
  bio?: string;
  location?: string;
  roles?: string[];
}

interface InfoItemProps {
  icon: React.ReactNode;
  label: string;
  value: string;
  editable: boolean;
}

const InfoItem: React.FC<InfoItemProps> = ({
  icon,
  label,
  value,
  editable,
}) => {
  const [isEditing, setIsEditing] = useState(false);
  const [inputValue, setInputValue] = useState(value);

  return (
    <div className="flex items-start gap-3 p-3 rounded-lg hover:bg-gray-50 transition-all">
      <div className="text-gray-400 mt-1">{icon}</div>
      <div className="flex-1">
        <p className="text-sm text-gray-500 font-medium">{label}</p>
        {editable && isEditing ? (
          <input
            type="text"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            className="mt-1 w-full px-3 py-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            onBlur={() => setIsEditing(false)}
            autoFocus
          />
        ) : (
          <p
            className={`mt-1 text-gray-900 font-medium ${editable ? "cursor-pointer hover:text-blue-600" : ""}`}
            onClick={() => editable && setIsEditing(true)}
          >
            {value || "Not set"}
          </p>
        )}
      </div>
      {editable && !isEditing && (
        <button
          onClick={() => setIsEditing(true)}
          className="text-gray-400 hover:text-blue-500 transition-all"
        >
          <Edit2 className="w-4 h-4" />
        </button>
      )}
    </div>
  );
};

export default function Page() {
  const [profile, setProfile] = useState<ProfileData | null>(null);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      setLoading(true);
      const response = await fetch(
        "http://localhost:9990/auth/oauth2/authenticated/me",
        {
          method: "GET",
          credentials: "include", // Vital for session-based OAuth2
          headers: {
            Accept: "application/json",
            "Cache-Control": "no-cache",
          },
        },
      );

      if (response.status === 401) {
        // Redirect to your login page if session expired
        window.location.href =
          "http://localhost:9990/oauth2/authorization/keycloak"; // or your provider
        return;
      }

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(
          errorData.message || `Server error: ${response.status}`,
        );
      }

      const data = await response.json();
      setProfile(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : "An error occurred");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 border-4 border-blue-500 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
          <p className="text-gray-600 font-medium">Loading profile...</p>
        </div>
      </div>
    );
  }

  if (error || !profile) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 flex items-center justify-center">
        <div className="text-center bg-white p-8 rounded-2xl shadow-lg">
          <p className="text-red-500 font-medium mb-4">
            Failed to load profile
          </p>
          <p className="text-gray-600 text-sm mb-4">{error}</p>
          <button
            onClick={fetchProfile}
            className="px-6 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-lg transition-all"
          >
            Try Again
          </button>
        </div>
      </div>
    );
  }

  const fullName = `${profile.givenName} ${profile.familyName}`;
  const initials = `${profile.givenName?.[0] || ""}${profile.familyName?.[0] || ""}`;

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 py-8 px-4">
      <div className="max-w-5xl mx-auto">
        {/* Cover Image Section */}
        <div className="bg-white rounded-t-3xl shadow-lg overflow-hidden">
          <div className="relative h-64 bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500">
            {profile.coverImage ? (
              <img
                src={profile.coverImage}
                alt="Cover"
                className="w-full h-full object-cover"
              />
            ) : (
              <div className="absolute inset-0 flex items-center justify-center">
                <div className="text-white text-opacity-30 text-6xl font-bold">
                  {initials}
                </div>
              </div>
            )}
            <button className="absolute bottom-4 right-4 bg-white bg-opacity-90 hover:bg-opacity-100 text-gray-700 px-4 py-2 rounded-lg shadow-lg flex items-center gap-2 transition-all">
              <Camera className="w-4 h-4" />
              Change Cover
            </button>
          </div>

          {/* Profile Header */}
          <div className="relative px-8 pb-8">
            <div className="flex flex-col md:flex-row md:items-end md:justify-between -mt-16 md:-mt-20">
              {/* Profile Picture */}
              <div className="relative group">
                <div className="w-32 h-32 md:w-40 md:h-40 rounded-full border-4 border-white shadow-xl bg-gradient-to-br from-blue-400 to-purple-500 flex items-center justify-center overflow-hidden">
                  {profile.profileImage ? (
                    <img
                      src={profile.profileImage}
                      alt="Profile"
                      className="w-full h-full object-cover"
                    />
                  ) : (
                    <span className="text-white text-4xl md:text-5xl font-bold">
                      {initials}
                    </span>
                  )}
                </div>
                <button className="absolute bottom-2 right-2 bg-blue-500 hover:bg-blue-600 text-white p-2 rounded-full shadow-lg transition-all">
                  <Camera className="w-4 h-4" />
                </button>
              </div>

              {/* Edit Profile Button */}
              <button
                onClick={() => setIsEditing(!isEditing)}
                className="mt-4 md:mt-0 bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded-xl shadow-lg flex items-center gap-2 justify-center transition-all"
              >
                <Edit2 className="w-4 h-4" />
                {isEditing ? "Cancel" : "Edit Profile"}
              </button>
            </div>

            {/* Name and Bio */}
            <div className="mt-6">
              <h1 className="text-3xl md:text-4xl font-bold text-gray-900">
                {fullName}
              </h1>
              <p className="text-blue-600 font-medium mt-1">
                @{profile.username}
              </p>

              {/* Roles Badges */}
              {profile.roles && profile.roles.length > 0 && (
                <div className="flex flex-wrap gap-2 mt-3">
                  {profile.roles.map((role, index) => (
                    <span
                      key={index}
                      className={`px-3 py-1 rounded-full text-xs font-semibold ${
                        role === "SUPER_ADMIN"
                          ? "bg-red-100 text-red-700"
                          : "bg-blue-100 text-blue-700"
                      }`}
                    >
                      {role.replace("_", " ")}
                    </span>
                  ))}
                </div>
              )}

              {profile.bio && (
                <p className="text-gray-600 mt-4 max-w-2xl leading-relaxed">
                  {profile.bio}
                </p>
              )}
            </div>
          </div>
        </div>

        {/* Profile Information Cards */}
        <div className="grid md:grid-cols-2 gap-6 mt-6">
          {/* Personal Information */}
          <div className="bg-white rounded-2xl shadow-lg p-6">
            <h2 className="text-xl font-bold text-gray-900 mb-6 flex items-center gap-2">
              <User className="w-5 h-5 text-blue-500" />
              Personal Information
            </h2>

            <div className="space-y-4">
              <InfoItem
                icon={<User className="w-5 h-5" />}
                label="Full Name"
                value={fullName}
                editable={isEditing}
              />
              <InfoItem
                icon={<User className="w-5 h-5" />}
                label="Username"
                value={profile.username}
                editable={false}
              />
              <InfoItem
                icon={<Mail className="w-5 h-5" />}
                label="Email"
                value={profile.email}
                editable={isEditing}
              />
              <InfoItem
                icon={<Phone className="w-5 h-5" />}
                label="Phone"
                value={profile.phoneNumber}
                editable={isEditing}
              />
            </div>
          </div>

          {/* Additional Details */}
          <div className="bg-white rounded-2xl shadow-lg p-6">
            <h2 className="text-xl font-bold text-gray-900 mb-6 flex items-center gap-2">
              <Briefcase className="w-5 h-5 text-purple-500" />
              Additional Details
            </h2>

            <div className="space-y-4">
              <InfoItem
                icon={<User className="w-5 h-5" />}
                label="Gender"
                value={profile.gender}
                editable={isEditing}
              />
              <InfoItem
                icon={<Calendar className="w-5 h-5" />}
                label="Date of Birth"
                value={
                  profile.dob
                    ? new Date(profile.dob).toLocaleDateString("en-US", {
                        year: "numeric",
                        month: "long",
                        day: "numeric",
                      })
                    : "Not set"
                }
                editable={isEditing}
              />
              <InfoItem
                icon={<MapPin className="w-5 h-5" />}
                label="Location"
                value={profile.location || "Not set"}
                editable={isEditing}
              />
              <InfoItem
                icon={<Briefcase className="w-5 h-5" />}
                label="Bio"
                value={profile.bio || "Not set"}
                editable={isEditing}
              />
            </div>
          </div>
        </div>

        {/* Save Changes Button */}
        {isEditing && (
          <div className="mt-6 flex gap-4 justify-end">
            <button
              onClick={() => setIsEditing(false)}
              className="px-6 py-3 bg-gray-200 hover:bg-gray-300 text-gray-700 rounded-xl font-medium transition-all"
            >
              Cancel
            </button>
            <button className="px-6 py-3 bg-gradient-to-r from-blue-500 to-purple-500 hover:from-blue-600 hover:to-purple-600 text-white rounded-xl font-medium shadow-lg transition-all">
              Save Changes
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
