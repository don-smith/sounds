# Sounds with SuperCollider

This is currently my only repo for my [SuperCollider](https://supercollider.github.io) files.

## Learning

I consider my teacher to be [Eli Fieldsteel](https://www.elifieldsteel.com) although he most likely doesn't know it (or even know me). If you're interested in learning SuperCollider, I can't recommend his [tutorial series](https://www.youtube.com/watch?v=yRzsOOiJ_p4&list=PLPYzvS8A_rTaNDweXe6PX4CXSGq4iEWYC) enough. He is a master educator and talented composer/musician. But don't be surprised if you need to watch them over and over and over.

I'm also currently learning a lot about [live coding](https://toplap.org) from [Sean Cotterill](https://seancotterill.xyz)'s excellent resources on [how to live code](https://theseanco.github.io/howto_co34pt_liveCode) with SuperCollider.

## Environment

I use [Neovim](https://neovim.io) as my primary coding environment so I use the [scvim](https://github.com/supercollider/scvim) plugin rather than the official SuperCollider IDE. However, I have altered the default key commands:

- `,ss` stops all sound
- `,l` evaluates the current line
- `,b` evaluates the current block or selected text
- `K` still launches the docs for the word under the cursor

If you aren't me and curious how I did this, you can find the settings in my [vim config](https://github.com/don-smith/dotfiles/blob/204e55595454fb2fd83be4afb3a01cf2756a5d89/config/nvim/init.vim#L155)

I also have to remember to run `:SClangStart` after I open a file. (Yes, these notes are for future me.) I also use [tmux](https://github.com/tmux/tmux) to manage multiple terminals. So if I want to move SuperCollider's logging pane, I need to:

1. Create the new pane with: `Ctrl-b c`
2. Note the number of the window. The next step will assume `1`
3. Move it to the new window with: `Ctrl-b :join-pane -t :1`

(Thank you past me :heart_eyes:)
