# Dicoding_GithubUser_03

<p align="center">
  <img src="https://user-images.githubusercontent.com/32255348/113876936-6b16ee00-97e2-11eb-8607-03e08995b57f.gif" width="200" />
</p>

<p align="center"><i><b>Figure 1</b> Preview of Github User App</i></p>

<br/>

Result of Renew my Dicoding: "Belajar Fundamental Aplikasi Android" Certificate (Final Submission: Aplikasi Github User -> Result)

## Information
<p align="center">
  <img src="https://1000logos.net/wp-content/uploads/2016/10/Android-Logo.png" width="200"/>
</p>
<p align="center"><i><b>Figure 2</b> Android Logo</i></p>

Type                  : Final Submission

Platform              : Mobile - [Android](https://www.android.com/intl/id_id/)

Programming Language  : [Kotlin](https://developer.android.com/kotlin?hl=id)

Dicoding Class        : [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14)

## Preparations
If you want to simulate this application you need to prepare:
- **Github Token API** for access data and upgrade Rate Limit when using Github API (Check: [What is Rate Limit in Github?](https://www.dicoding.com/blog/apa-itu-rate-limit-pada-github-api/))
- Create **apikeys.properties** for **app module** and **consumer module** in app folder (Project->app main-> right click-> New -> File) and Add your github API token (Exp: GITHUB_API_KEY = "#################################")
- Change main username (current is my github username: patriciafiona) to your github username

Example of the end point:
- **Search** : https://api.github.com/search/users?q={username}
- **Detail user** : https://api.github.com/users/{username}
- **List Follower** : https://api.github.com/users/{username}/followers
- **List Following** : https://api.github.com/users/{username}/following

For the details you can check [Github End Point Documentation](https://docs.github.com/en/rest/overview/endpoints-available-for-github-apps)
and for more details about Github API [here](https://docs.github.com/en/rest)

## Results for App Module
| Action                            | Result                                  | Action                            | Result                                  |
| -------------                     |------------------                       | -------------                     |------------------                       |
| Main Preview                      | <img src="https://user-images.githubusercontent.com/32255348/113876936-6b16ee00-97e2-11eb-8607-03e08995b57f.gif" width="150" />      | Add & Remove Reminder             | <img src="https://user-images.githubusercontent.com/32255348/113877860-4bcc9080-97e3-11eb-8183-fad819a3d135.gif" width="150" />      |
| Add & Remove User Favorite        | <img src="https://user-images.githubusercontent.com/32255348/113877831-440cec00-97e3-11eb-9031-c3d1ae2cc991.gif" width="150" />      |  Change Language                   | <img src="https://user-images.githubusercontent.com/32255348/113877803-3f483800-97e3-11eb-9a83-d98c839187cb.gif" width="150" />      |
| Search User By Username           | <img src="https://user-images.githubusercontent.com/32255348/113877872-4ec78100-97e3-11eb-899c-005402c86dd4.gif" width="150" />      | See List of Following & Followers | <img src="https://user-images.githubusercontent.com/32255348/113877881-51c27180-97e3-11eb-9d16-eeaba726d190.gif" width="150" />      |
| Setting Page Preview              | <img src="https://user-images.githubusercontent.com/32255348/113877937-5ab34300-97e3-11eb-81b5-5dbee0d6aad8.gif" width="150" />      | No Internet Connection            | <img src="https://user-images.githubusercontent.com/32255348/113877848-48d1a000-97e3-11eb-8f3b-69438461b36a.gif" width="150" />      |

## Results for Consumer Module & User Favorite Widget
| Action                            | Result           | Action                            | Result           |
| -------------                     |------------------| -------------                     |------------------|
| Consumer Module Preview           | <img src="https://user-images.githubusercontent.com/32255348/113879563-d5c92900-97e4-11eb-8f27-d499b9d80bd2.gif" width="150" />      | User Favorite Widget Preview                      | <img src="https://user-images.githubusercontent.com/32255348/113879581-db267380-97e4-11eb-8ea9-57e52011ab3e.gif" width="150" />      |
